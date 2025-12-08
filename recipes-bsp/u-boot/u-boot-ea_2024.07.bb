require recipes-bsp/u-boot/u-boot-renesas.inc

COMPATIBLE_MACHINE = "(rzg3e-family)"

UBOOT_URI = "git://github.com/embeddedartists/uboot-rz.git;protocol=https;nobranch=1"
UBOOT_REV ?= "95c056fadd753b60fcaefe844f13849dd3c6c972"

PV="2024.07+git${SRCPV}"

LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
