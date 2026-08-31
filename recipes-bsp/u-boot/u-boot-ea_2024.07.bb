require recipes-bsp/u-boot/u-boot-renesas.inc

COMPATIBLE_MACHINE = "(rzg3e-family|rzg3l-family)"

UBOOT_URI = "git://github.com/embeddedartists/uboot-rz.git;protocol=https;nobranch=1"
UBOOT_REV ?= "3c591cb01520cefdeb78a2e3da18c8f72f145408"
UBOOT_REV:rzg3l-ea-som  = "09306f69a0f347979f9010eb21635f74d6017d78"

PV="2024.07+git${SRCPV}"

LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
