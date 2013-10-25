#!/bin/bash

echo Optimizing code for $1.spim
java Parser ./test_cases/$1.spim > ./test_out/$1.spim
