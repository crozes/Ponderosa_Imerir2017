cmake_minimum_required(VERSION 3.7)
project(TestCURL)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES main.c)
add_executable(TestCURL ${SOURCE_FILES})
target_link_libraries(TestCURL libcurl.so)