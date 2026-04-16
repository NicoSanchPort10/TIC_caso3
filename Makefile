SRC := $(shell find src -name "*.java")
OUT := out
CONFIG ?= config/ejemplo.properties

build:
	mkdir -p $(OUT)
	javac -d $(OUT) $(SRC)

run: build
	java -cp $(OUT) caso3.Main $(CONFIG)

clean:
	rm -rf $(OUT)

.PHONY: build run clean
