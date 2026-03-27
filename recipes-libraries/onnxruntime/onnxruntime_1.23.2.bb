SUMMARY = "ONNX Runtime recipe"
HOMEPAGE = "https://onnxruntime.ai/"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0f7e3b1308cb5c00b372a6e78835732d"

SRCREV = "a83fc4d58cb48eb68890dd689f94f28288cf2278"

SRC_URI = " \
    git://github.com/microsoft/onnxruntime.git;branch=rel-1.23.2;protocol=https \
"

#protobuf-native

S = "${WORKDIR}/git"

inherit cmake python3native python3-dir

OECMAKE_SOURCEPATH = "${S}/cmake"
OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE=" \
    -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
    --compile-no-warning-as-error \
    -Donnxruntime_DISABLE_RTTI=OFF \
    -DCMAKE_BUILD_TYPE=Release \
"

PYTHON_DEPENDS = "\
    ${PYTHON_PN} \
    ${PYTHON_PN}-pip-native \
    ${PYTHON_PN}-numpy \
    ${PYTHON_PN}-numpy-native \
    ${PYTHON_PN}-packaging-native\
    ${PYTHON_PN}-pybind11\
    ${PYTHON_PN}-pybind11-native\
"

PYTHON_RDEPENDS = "\
    ${PYTHON_PN} \
    ${PYTHON_PN}-numpy \
    ${PYTHON_PN}-protobuf \
    ${PYTHON_PN}-coloredlogs \
    ${PYTHON_PN}-flatbuffers \
    ${PYTHON_PN}-sympy \
"

PACKAGECONFIG ?= "crosscompiling sharedlib python ${PACKAGECONFIG_NPU}"
PACKAGECONFIG_NPU                    = ""

PACKAGECONFIG[nsync] = "-Donnxruntime_USE_NSYNC=ON, -Donnxruntime_USE_NSYNC=OFF"
PACKAGECONFIG[prebuilt] = "-Donnxruntime_USE_PREBUILT_PB=ON, -Donnxruntime_USE_PREBUILT_PB=OFF"
PACKAGECONFIG[openmp] = "-Donnxruntime_USE_OPENMP=ON, -Donnxruntime_USE_OPENMP=OFF"
PACKAGECONFIG[trt] = "-Donnxruntime_USE_TRT=ON, -Donnxruntime_USE_TRT=OFF"
PACKAGECONFIG[nuphar] = "-Donnxruntime_USE_NUPHAR=ON, -Donnxruntime_USE_NUPHAR=OFF"
PACKAGECONFIG[brainslice] = "-Donnxruntime_USE_BRAINSLICE=ON, -Donnxruntime_USE_BRAINSLICE=OFF"
PACKAGECONFIG[python] = "-Donnxruntime_ENABLE_PYTHON=ON, -Donnxruntime_ENABLE_PYTHON=OFF, ${PYTHON_DEPENDS}, ${PYTHON_RDEPENDS}"
PACKAGECONFIG[sharedlib] = "-Donnxruntime_BUILD_SHARED_LIB=ON, -Donnxruntime_BUILD_SHARED_LIB=OFF"
PACKAGECONFIG[eigenblas] = "-Donnxruntime_USE_EIGEN_FOR_BLAS=ON, -Donnxruntime_USE_EIGEN_FOR_BLAS=OFF"
PACKAGECONFIG[openblas] = "-Donnxruntime_USE_OPENBLAS=ON, -Donnxruntime_USE_OPENBLAS=OFF"
PACKAGECONFIG[dnnl] = "-Donnxruntime_USE_DNNL=ON, -Donnxruntime_USE_DNNL=OFF"
PACKAGECONFIG[mklml] = "-Donnxruntime_USE_MKLML=ON, -Donnxruntime_USE_MKLML=OFF"
PACKAGECONFIG[gemmlowp] = "-Donnxruntime_USE_GEMMLOWP=ON, -Donnxruntime_USE_GEMMLOWP=OFF"
PACKAGECONFIG[ngraph] = "-Donnxruntime_USE_NGRAPH=ON, -Donnxruntime_USE_NGRAPH=OFF"
PACKAGECONFIG[openvino] = "-Donnxruntime_USE_OPENVINO=ON, -Donnxruntime_USE_OPENVINO=OFF"
PACKAGECONFIG[interop] = "-Donnxruntime_ENABLE_LANGUAGE_INTEROP_OPS=ON, -Donnxruntime_ENABLE_LANGUAGE_INTEROP_OPS=OFF"
PACKAGECONFIG[dml] = "-Donnxruntime_USE_DML=ON, -Donnxruntime_USE_DML=OFF"
PACKAGECONFIG[telemetry] = "-Donnxruntime_USE_TELEMETRY=ON, -Donnxruntime_USE_TELEMETRY=OFF"
PACKAGECONFIG[armnn-relu] = "-Donnxruntime_ARMNN_RELU_USE_CPU=ON, -Donnxruntime_ARMNN_RELU_USE_CPU=OFF"
PACKAGECONFIG[armnn-bn] = "-Donnxruntime_ARMNN_BN_USE_CPU=ON, -Donnxruntime_ARMNN_BN_USE_CPU=OFF"
PACKAGECONFIG[opschema] = "-Donnxruntime_PYBIND_EXPORT_OPSCHEMA=ON, -Donnxruntime_PYBIND_EXPORT_OPSCHEMA=OFF"
PACKAGECONFIG[nnapi] = "-Donnxruntime_USE_NNAPI_BUILTIN=ON, -Donnxruntime_USE_NNAPI_BUILTIN=OFF"
PACKAGECONFIG[tvm] = "-Donnxruntime_USE_TVM=ON, -Donnxruntime_USE_TVM=OFF"
PACKAGECONFIG[llvm] = "-Donnxruntime_USE_LLVM=ON, -Donnxruntime_USE_LLVM=OFF"
PACKAGECONFIG[microsoft] = "-Donnxruntime_ENABLE_MICROSOFT_INTERNAL=ON, -Donnxruntime_ENABLE_MICROSOFT_INTERNAL=OFF"
PACKAGECONFIG[eigenthreadpool] = "-Donnxruntime_USE_EIGEN_THREADPOOL=ON, -Donnxruntime_USE_EIGEN_THREADPOOL=OFF"
PACKAGECONFIG[tensorrt] = "-Donnxruntime_USE_TENSORRT=ON, -Donnxruntime_USE_TENSORRT=OFF"
PACKAGECONFIG[crosscompiling] = "-Donnxruntime_CROSS_COMPILING=ON, -Donnxruntime_CROSS_COMPILING=OFF "
PACKAGECONFIG[server] = "-Donnxruntime_BUILD_SERVER=ON, -Donnxruntime_BUILD_SERVER=OFF"
PACKAGECONFIG[x86] = "-Donnxruntime_BUILD:x86=ON, -Donnxruntime_BUILD:x86=OFF"
PACKAGECONFIG[fullprotobuf] = "-Donnxruntime_USE_FULL_PROTOBUF=ON, -Donnxruntime_USE_FULL_PROTOBUF=OFF"
PACKAGECONFIG[ops] = "-Donnxruntime_DISABLE_CONTRIB_OPS=ON, -Donnxruntime_DISABLE_CONTRIB_OPS=OFF"
PACKAGECONFIG[staticruntime] = "-Donnxruntime_MSVC_STATIC_RUNTIME=ON, -Donnxruntime_MSVC_STATIC_RUNTIME=OFF"
PACKAGECONFIG[runtests] = "-Donnxruntime_RUN_ONNX_TESTS=ON, -Donnxruntime_RUN_ONNX_TESTS=OFF"
PACKAGECONFIG[reports] = "-Donnxruntime_GENERATE_TEST_REPORTS=ON, -Donnxruntime_GENERATE_TEST_REPORTS=OFF"
PACKAGECONFIG[devmode] = "-Donnxruntime_DEV_MODE=ON, -Donnxruntime_DEV_MODE=OFF"
PACKAGECONFIG[cuda] = "-Donnxruntime_USE_CUDA=ON, -Donnxruntime_USE_CUDA=OFF"
PACKAGECONFIG[automl] = "-Donnxruntime_USE_AUTOML=ON, -Donnxruntime_USE_AUTOML=OFF"
PACKAGECONFIG[jemalloc] = "-Donnxruntime_USE_JEMALLOC=ON, -Donnxruntime_USE_JEMALLOC=OFF"
PACKAGECONFIG[mimalloc] = "-Donnxruntime_USE_MIMALLOC=ON, -Donnxruntime_USE_MIMALLOC=OFF"
PACKAGECONFIG[csharp] = "-Donnxruntime_BUILD_CSHARP=ON, -Donnxruntime_BUILD_CSHARP=OFF"
PACKAGECONFIG[java] = "-Donnxruntime_BUILD_JAVA=ON, -Donnxruntime_BUILD_JAVA=OFF"
PACKAGECONFIG[kleidiai] = "-Donnxruntime_USE_KLEIDIAI=ON, -Donnxruntime_USE_KLEIDIAI=OFF"
PACKAGECONFIG[neutron] = "-Donnxruntime_USE_NEUTRON=ON, -Donnxruntime_USE_NEUTRON=OFF, neutron nlohmann-json"
PACKAGECONFIG[vsinpu] = "-Donnxruntime_USE_VSINPU=ON, -Donnxruntime_USE_VSINPU=OFF, tim-vx"

PACKAGES += "${PN}-python ${PN}-perf"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}/*"
FILES:${PN}-perf += "${bindir}/onnxruntime_perf_test"
RDEPENDS:${PN}-python += "${PYTHON_RDEPENDS}"

do_configure[network] = "1"
do_configure:prepend() {
    export HTTP_PROXY=${http_proxy}
    export HTTPS_PROXY=${https_proxy}
    export http_proxy=${http_proxy}
    export https_proxy=${https_proxy}
}

do_install:append() {
    install -d ${D}${bindir}
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi

    if [ -f ${B}/onnxruntime_perf_test ]; then
        install -m 0755 ${B}/onnxruntime_perf_test ${D}${bindir}/
    elif [ -f ${B}/Release/onnxruntime_perf_test ]; then
        install -m 0755 ${B}/Release/onnxruntime_perf_test ${D}${bindir}/
    fi

    #
    # Top-level package file
    #
    install -m 0644 ${B}/onnxruntime/__init__.py \
        ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/

    #
    # capi package files
    #

    install -m 0644 ${B}/onnxruntime/capi/__init__.py \
        ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/
    install -m 0644 ${B}/onnxruntime/capi/_pybind_state.py \
        ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/
    install -m 0644 ${B}/onnxruntime/capi/onnxruntime_inference_collection.py \
        ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/
    install -m 0644 ${B}/onnxruntime/capi/onnxruntime_validation.py \
        ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/

    if [ -f ${B}/onnxruntime/capi/onnxruntime_collect_build_info.py ]; then
        install -m 0644 ${B}/onnxruntime/capi/onnxruntime_collect_build_info.py \
            ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/
    fi

    if [ -f ${B}/onnxruntime/capi/_ld_preload.py ]; then
        install -m 0644 ${B}/onnxruntime/capi/_ld_preload.py \
            ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/
    fi

    install -m 0755 ${B}/onnxruntime/capi/onnxruntime_pybind11_state.so \
        ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/

    # package/version metadata used by onnxruntime_validation.py
    if [ -f ${B}/onnxruntime/capi/build_and_package_info.py ]; then
        install -m 0644 ${B}/onnxruntime/capi/build_and_package_info.py \
            ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/
    else
        cat > ${D}${PYTHON_SITEPACKAGES_DIR}/onnxruntime/capi/build_and_package_info.py <<EOF
package_name = "onnxruntime"
__version__ = "1.23.2"
EOF
    fi

}

# QA Issue: -dev package onnxruntime-dev contains non-symlink .so '/usr/lib/libonnxruntime_providers_shared.so' [dev-elf]
# This lib is being packaged into -dev. This is intended
INSANE_SKIP:${PN}-dev += "dev-elf"
