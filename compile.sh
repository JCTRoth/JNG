#!/bin/sh
mkdir -p bin
javac src/jng/ui/Jng.java -d bin -sourcepath src/ -cp "bin:jars/hamcrest-core-1.3.jar:jars/hamcrest-library-1.3.jar:jars/jogg-0.0.7.jar:jars/jorbis-0.0.15.jar:jars/junit-4.11.jar:jars/slick.jar:jars/eea.jar:jars/lwjgl.jar:"
