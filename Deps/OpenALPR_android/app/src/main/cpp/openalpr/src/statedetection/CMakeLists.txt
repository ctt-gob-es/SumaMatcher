


set(statedetector_source_files
  state_detector.cpp
  featurematcher.cpp
  line_segment.cpp
  state_detector_impl.cpp
)

if (WIN32 OR IOS) 
	add_library(statedetection 		STATIC ${statedetector_source_files} )
ELSE()
	add_library(statedetection 		SHARED ${statedetector_source_files} )
ENDIF()

set_target_properties(statedetection PROPERTIES SOVERSION ${OPENALPR_MAJOR_VERSION})

set(OPENCV_LIB_PATH "${PROJECT_SOURCE_DIR}/opencv/${ANDROID_ABI}/libopencv_java4.so")

TARGET_LINK_LIBRARIES(statedetection
   ${OPENCV_LIB_PATH}
   support
)


#install (FILES   state_detector.h     DESTINATION    ${CMAKE_INSTALL_PREFIX}/include)
#install (TARGETS statedetection   DESTINATION    ${CMAKE_INSTALL_PREFIX}/lib)


