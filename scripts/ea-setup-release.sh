#!/bin/sh
#
# Yocto Project Build Environment Setup Script
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA


CWD=`pwd`
CONF_MODIFIED="conf-modified"

clean_env()
{
    unset BUILD_DIR TEMPLATECONF
    unset show_help error opt
    unset MACHINE DISTRO
}

usage()
{
    echo -e "\nUsage: MACHINE=<machine> source ea-setup-release.sh
    Optional parameters: [-b build-dir] [-h]"
    echo "
    * [-b build-dir]: Build directory, if unspecified script uses 'build' as output directory
    * [-h]: help
    "
}

show_help=false
error=false
modify_config=false

# --- parse options (save/restore OPTIND so sourcing multiple times works) ---
OLD_OPTIND=$OPTIND
OPTIND=1

while getopts "b:h" opt
do
    case $opt in
        b) BUILD_DIR="$OPTARG" ;;
        h) show_help='true' ;;
        \?) error='true' ;;
    esac
done

shift "$((OPTIND - 1))"
OPTIND=$OLD_OPTIND

# flag any unexpected positional args
if (( $# > 0 )); then
  error=true
  printf 'Invalid extra arguments: %q\n' "$*"
fi

# decide what to do
if $show_help; then
  usage
  clean_env
  return 1 2>/dev/null || exit 1
elif $error; then
  clean_env
  return 1 2>/dev/null || exit 1
fi

# defaults after parsing
: "${BUILD_DIR:=build}"

if [ -z "$DISTRO" ]; then
  # As examples for RZ MPU, "rz-bsp" and "rz-vlp" are provided which are based
  # on "poky". "rz-bsp" is minimum settings and "rz-vlp" is added some useful
  # tools for the verification(includes "rz-bsp").
  DISTRO="rz-vlp"
fi

if [ -z "$MACHINE" ]; then
    MACHINE='rzg3e-ea-som'
fi

# templates for local.conf and bblayers.conf
if [ -z "$TEMPLATECONF" ]; then
    TEMPLATECONF=$PWD/sources/meta-renesas/meta-rz-distro/conf/templates/rz-conf/
fi

# OpenEmbedded setup script
. $PWD/sources/poky/oe-init-build-env $CWD/$BUILD_DIR > /dev/null

# Point to current since oe-init-build-env will change directory to $BUILD_DIR
BUILD_DIR=.

if [ ! -e conf/$CONF_MODIFIED ]; then
    modify_config=true
fi

if $modify_config; then
    # keep a copy of original
    mv conf/local.conf conf/local.conf.sample

    # remove all comments
    grep -v '^#\|^$' conf/local.conf.sample > conf/local.conf

    # Change settings according environment
    sed -e "s,MACHINE ??=.*,MACHINE ??= '$MACHINE',g" \
        -e "s,DISTRO ?=.*,DISTRO ?= '$DISTRO',g" \
        -i conf/local.conf

    echo "" >> conf/local.conf
    echo "LICENSE_FLAGS_ACCEPTED:append = \" commercial_ffmpeg commercial_x264 \"" >> conf/local.conf

    # xxx
    sed -i 's|${TOPDIR}/\.\./|${TOPDIR}/../sources/|g' conf/bblayers.conf

    # Add EA layers
    echo "" >> $BUILD_DIR/conf/bblayers.conf
    echo "BBLAYERS += \"\${TOPDIR}/../sources/meta-ea-rz \"" >> $BUILD_DIR/conf/bblayers.conf
    echo "BBLAYERS += \"\${TOPDIR}/../sources/meta-ea-dx \"" >> $BUILD_DIR/conf/bblayers.conf

    # Add proprietary Renesas layers, downloadable from https://www.renesas.com/en/software-tool/rzg3e-board-support-package
    echo "BBLAYERS += \"\${TOPDIR}/../sources/meta-rz-features/meta-rz-codecs \"" >> $BUILD_DIR/conf/bblayers.conf
    echo "BBLAYERS += \"\${TOPDIR}/../sources/meta-rz-features/meta-rz-graphics \"" >> $BUILD_DIR/conf/bblayers.conf

    # Add MemryX (either add meta-ea-dx or these two for MemryX - can't use both at the same time)
    echo "" >> $BUILD_DIR/conf/bblayers.conf
    echo "# Pick either meta-ea-dx or the memx repos. Cannot have both at the same time." >> $BUILD_DIR/conf/bblayers.conf
    echo "#BBLAYERS += \"\${TOPDIR}/../sources/meta-ea-memx \"" >> $BUILD_DIR/conf/bblayers.conf
    echo "#BBLAYERS += \"\${TOPDIR}/../sources/memx-yocto-renesas/meta-memx-runtime \"" >> $BUILD_DIR/conf/bblayers.conf
    echo "#BBLAYERS += \"\${TOPDIR}/../sources/memx-yocto-renesas/meta-mx3-driver \"" >> $BUILD_DIR/conf/bblayers.conf

    echo 'generated' >> conf/$CONF_MODIFIED
fi

cat <<EOF

The Yocto Project has extensive documentation about OE including a
reference manual which can be found at:
    http://yoctoproject.org/documentation

For more information about OpenEmbedded see their website:
    http://www.openembedded.org/

You can now run 'bitbake <target>'

Common targets are:
    ea-image-base
    core-image-minimal
    core-image-weston
    meta-toolchain

EOF

if $modify_config; then
    cat <<EOF
Your build environment has been configured with:

    MACHINE=$MACHINE
    DISTRO=$DISTRO
EOF
else
    echo "Your configuration files have not been touched."
fi

clean_env
