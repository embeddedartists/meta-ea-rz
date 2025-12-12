DESCRIPTION = "Linux Kernel for Embedded Artists RZ based SOM boards. \
The kernel is based on the kernel provided by Renesas."

require recipes-kernel/linux/linux-yocto.inc

DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native libmpc-native"

SRC_URI = "${KERNEL_URL};protocol=https;branch=${KERNEL_BRANCH}"
SRCREV = "${KERNEL_REV}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PV = "${LINUX_VERSION}+git${SRCPV}"

LINUX_VERSION ?= "6.1.107-cip28"
KBUILD_DEFCONFIG ?= "defconfig"
KCONFIG_MODE ?= "alldefconfig"

KERNEL_URL ?= "git://github.com/embeddedartists/linux-rz.git"

KERNEL_BRANCH ?= "ea-6.1"
KERNEL_REV ?= "5749d502fc942729043bad14d87e02b8157614f6"

#SRC_URI:append = "${@bb.utils.contains('DISTRO_FEATURES','docker', ' file://docker.cfg', '', d)}"

# To enable support for the Telit Cinterion FN990A40-HP 5G sub6 M.2 modem, uncomment these two lines:
#FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
#SRC_URI += "file://Telit_Cinterion_FN990A40-HP_5G_sub6_M2-fragment.cfg"
