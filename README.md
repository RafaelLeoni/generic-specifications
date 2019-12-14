# Generic Specifications
This project is to use generic specifications of Spring Data JPA to query any parameter

## Install
To install clone the project and then run the gradle build command:

> $	git clone https://github.com/RafaelLeoni/generic-specifications.git
> $	cd generic-specifications
> $  gradle build

## Operations
This table shows a list of the current supported operations and the ones that still need implementation.

| Operation				   | Code | Implemented |
| ------------------------ | ---- | ----------- |
| equal 				   | eq   |	Yes      	|
| not equal 			   | neq  |	No			|
| greater than 			   | gt	  |	Yes 		|
| greater than or equal to | gte  |	No 			|
| less than 			   | lt	  |	Yes 		|
| less than or equal to	   | lte  |	No 			|
| in 					   | in   |	No 			|
| not in 				   | nin  |	No 			|
| between 				   | btn  |	No 			|
| like 					   | like |	No	 		|