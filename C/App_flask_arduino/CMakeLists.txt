cmake_minimum_required(VERSION 3.7)
project(App_flask_arduino)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c arduino-serial-lib.c arduino-serial-lib.h)
add_executable(App_flask_arduino ${SOURCE_FILES})
target_link_libraries(App_flask_arduino libcurl.so json-c.so)