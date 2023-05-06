#include <stdio.h>
#include <malloc.h>
#include <limits.h>
#include "mpi/mpi.h"

int search(int *arr, int N, int x) //search for index of the final max
{
    int i;
    for (i = 0; i < N; i++)
        if (arr[i] == x)
            return i;
    return -1;
}

int main() //Master Process is only included in the process when a remainder exists (subarray_size % (proc-1) != 0)
 {
     int my_rank, subarray_size , index; //rank of the process, size of the subarrays, index in the subarray
     int proc ,ind; //number of processes, index in the array

     MPI_Init(NULL, NULL);

     MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
     MPI_Comm_size(MPI_COMM_WORLD, &proc);

     int array_size;
     int max=INT_MIN;
     int *clone; //clone array to copy the main array


     if (my_rank != 0) //Slaves
    {

        int *arr2; //buffer to save the subarray
        arr2 = (int *)malloc(subarray_size * sizeof(int)); //allocation of subarray

        MPI_Recv(&subarray_size,1,MPI_INT,0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE); //receves subarray size

        MPI_Recv(arr2,subarray_size,MPI_INT,0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE); //receves subarray

        int i=0;
        for(;i<subarray_size;i++)
        {
            if(arr2[i]>max)
            {
                max=arr2[i];
                index=i;
            }
        }
        printf("Hello from slave  #%d Max number in my partition is %d and its index is %d\n", my_rank, max, index);

        MPI_Send(&max, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);

    
    }
    else //my_rank = 0 (Master Process)
    {

        printf("Hello from master process\n");

        printf("Number of slave processes %d \n", proc-1);

        printf("Please Enter size of array...\n");

        scanf("%d", &array_size);
        subarray_size=(array_size/(proc-1)); //subarray size divided over processes equally 

        int *arr;

        printf("Please Enter array elements...\n");

        arr=(int*)malloc(array_size* sizeof(int));
        clone=(int*)malloc(array_size* sizeof(int));


        for(int j=0;j<array_size;j++) //takes the array elements as an input
        {
            scanf("%d", &arr[j]);
        }

        
        for(int h=0;h<array_size;h++) //copies the array elements in clone array to search for index later
        {
            clone[h]=arr[h];
        }


        int k=1;
        for (; k < proc; k++) //for loop to divid the array among the elements
        {
            int num= (k-1)*subarray_size;

            MPI_Send(&subarray_size,1, MPI_INT,k,0,MPI_COMM_WORLD);

            MPI_Send(&arr[num],subarray_size, MPI_INT,k,0,MPI_COMM_WORLD);

            int MasterMax=INT_MIN; 
            if (subarray_size%(proc-1)!=0){ //the remaining elements (if any )is sent to the master process
                int count=subarray_size*(proc-1);

                for(;count<array_size;count++)
                {
                    if(arr[count]>MasterMax)
                    {
                        MasterMax=arr[count];
                    }
                }
            }

            int temp;

            MPI_Recv(&temp, 1, MPI_INT, k, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            if(temp>max){ 
                max=temp;
            }

            if(MasterMax>max){
                max=MasterMax;
            }


            ind =search(clone,array_size,max); //ind holds the index of the max in the main array 


        }

    }

     MPI_Finalize();

    if(my_rank == 0)
    {

        printf("Master process announce the final max which is %d and its index is %d.\n", max ,ind);
        printf("Thank you for using our program\n");
    }

     return 0;
}