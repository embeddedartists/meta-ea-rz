require recipes-bsp/u-boot/u-boot-renesas.inc

COMPATIBLE_MACHINE = "(rzg3e-family)"

UBOOT_URI = "git://github.com/embeddedartists/uboot-rz.git;protocol=https;nobranch=1"
UBOOT_REV ?= "5efb24a76fcffde2b0c2fec9ba8085b733f151eb"

PV="2024.07+git${SRCPV}"

LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
