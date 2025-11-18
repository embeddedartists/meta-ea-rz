#!/bin/sh

# Find the device and parition where the rootfs resides
#   root@smarc-rzg3e:~# blkid|grep 'LABEL="root"'
#   /dev/mmcblk1p2: LABEL="root" UUID="d4841ffe-05eb-455a-9366-6488a79a2152" TYPE="ext4"
#   root@smarc-rzg3e:~# blkid|grep 'LABEL="root"'|cut -f 1 -d ':'
#   /dev/mmcblk1p2
#   root@smarc-rzg3e:~# blkid|grep 'LABEL="root"'|cut -f 1 -d ':'|cut -f 1 -d 'p'
#   /dev/mmcblk1
devpath=`blkid|grep 'LABEL="root"'|cut -f 1 -d ':'|cut -f 1 -d 'p'`


# Find last partition
PART_NUM=$(parted $devpath -ms unit s p | tail -n 1 | cut -f 1 -d:)

# Extract the partition's start sector
PART_START=$(parted $devpath -ms unit s p | grep "^${PART_NUM}" | cut -f 2 -d:)

disk_size=`cat /sys/block/${devpath#/dev/}/size`
part_off=`cat /sys/block/${devpath#/dev/}/${devpath#/dev/}p${PART_NUM}/start`
part_size=`cat /sys/block/${devpath#/dev/}/${devpath#/dev/}p${PART_NUM}/size`
left=`expr $disk_size - $part_off - $part_size`
echo "DBG: $devpath, $PART_NUM, $PART_START, $disk_size, $part_off, $part_size, $left"  >> /tmp/ea-resizefs.log

# Skip resizing if close to max size already
if [ $left -lt 10240 ]; then
    echo "Skipping resizing of ${devpath}p${PART_NUM} - only $left blocks left"  >> /tmp/ea-resizefs.log
    exit 0
 fi

old=`df -h ${devpath}p${PART_NUM} | grep /dev/ |awk '{print $2}'`
echo "Resizing ${devpath}p${PART_NUM}. Old size ${old}" >> /tmp/ea-resizefs.log

# Remove the last partition
echo "Remove the last partition with parted" >> /tmp/ea-resizefs.log
parted $devpath -ms rm $PART_NUM >> /tmp/ea-resizefs.log 2>&1

# Create a new partition with the old partition's start sector
# but using 95% of the available space as size. The reason for
# not using the full 100% is that for uSD card cloning it is
# good if the image is undersized in case there is a variance
# (maybe just a couple of MB) between different manufacturers
# and/or models. For a demo platform this should be closer to
# the actual need so e.g. 60%.
echo "Create new partition $devpath $PART_START" >> /tmp/ea-resizefs.log
parted $devpath -ms unit s mkpart primary $PART_START 95% >> /tmp/ea-resizefs.log 2>&1

echo "Calling resize2fs ${devpath}p${PART_NUM}" >> /tmp/ea-resizefs.log
resize2fs ${devpath}p${PART_NUM} >> /tmp/ea-resizefs.log 2>&1

old=`df -h ${devpath}p${PART_NUM} | grep /dev/ |awk '{print $2}'`
echo "Finished resizing ${devpath}p${PART_NUM}. New size ${old}" >> /tmp/ea-resizefs.log

# Remove the service. Should only be run once
systemctl --no-reload disable resizefs.service
