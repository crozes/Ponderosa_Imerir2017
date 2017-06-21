cmake_minimum_required(VERSION 3.7)
project(App_flask_arduino)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c arduino-serial-lib.c arduino-serial-lib.h /curl-7.54.1/include/curl/*)
add_executable(App_flask_arduino ${SOURCE_FILES})