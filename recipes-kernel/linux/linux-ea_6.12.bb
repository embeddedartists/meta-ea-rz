DESCRIPTION = "Linux Kernel for Embedded Artists RZ based SOM boards. \
The kernel is based on the kernel provided by Renesas."

require recipes-kernel/linux/linux-yocto.inc

DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native libmpc-native"

SRC_URI = "${KERNEL_URL};protocol=https;branch=${KERNEL_BRANCH}"
SRCREV = "${KERNEL_REV}"

SRC_URI:append:rzg3l-family = " \
        file://0001-GPU-driver-remove-power-domains-of-GPU-note-for-RZG3L.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "${LINUX_VERSION}+git${SRCPV}"

LINUX_VERSION ?= "6.12.46-cip8"
KBUILD_DEFCONFIG ?= "defconfig"
KCONFIG_MODE ?= "alldefconfig"

KERNEL_URL ?= "git://github.com/embeddedartists/linux-rz.git"

KERNEL_BRANCH ?= "ea-6.12-cip8"
KERNEL_REV ?= "6880bcabc58925478a8e290a54af736cc66caf1d"

