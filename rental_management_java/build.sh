#!/bin/bash

# Compile
javac -cp ".:lib/json-simple-1.1.1.jar" RentalManagement.java

# Run
java -cp ".:lib/json-simple-1.1.1.jar" RentalManagement
