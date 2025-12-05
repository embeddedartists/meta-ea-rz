require recipes-bsp/u-boot/u-boot-renesas.inc

COMPATIBLE_MACHINE = "(rzg3e-family)"

UBOOT_URI = "git://github.com/embeddedartists/uboot-rz.git;protocol=https;nobranch=1"
UBOOT_REV ?= "f656672482611e49d90e0a244a52146a1aeef00f"

PV="2024.07+git${SRCPV}"

LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
