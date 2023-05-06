// make sure to import these libraries to avoid errors.
#include <mpi.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void print_matrix(int **matrix, int rows, int cols)
{
    for (int i = 0; i < rows; ++i)
    {
        for (int j = 0; j < cols; ++j)
        {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}

int main(int argc, char **argv)
{

    // Initialize the MPI environment
    MPI_Init(NULL, NULL);

    // Get the number of processes
    int size;
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    // Get the rank of the process
    int rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    // make variables for all processes.
    int rows1, col1, rows2, col2, rows3, col3;
    int **matrix1, **matrix2, **matrix3;
    int choice;

    // take variable input at master process.
    if (rank == 0)
    {
        
        printf("Enter 1 for file 2 for console: ");
        fflush(stdout);
        scanf("%d", &choice);
        if (choice == 1)
        {
            FILE *file;
            file = fopen("input.txt", "r");
            fscanf(file, "%d %d", &rows1, &col1);
            // building the 2d array.
            matrix1 = (int **)malloc(rows1 * sizeof(int *));
            for (int i = 0; i < rows1; i++)
                matrix1[i] = (int *)malloc(col1 * sizeof(int));
            for (size_t i = 0; i < rows1; i++)
            {
                for (size_t j = 0; j < col1; j++)
                {
                    fscanf(file, "%d", &matrix1[i][j]);
                }
            }
            fscanf(file, "%d %d", &rows2, &col2);
            // building the 2d array.
            matrix2 = (int **)malloc(rows2 * sizeof(int *));
            for (int i = 0; i < rows2; i++)
                matrix2[i] = (int *)malloc(col2 * sizeof(int));
            for (size_t i = 0; i < rows2; i++)
            {
                for (size_t j = 0; j < col2; j++)
                {
                    fscanf(file, "%d", &matrix2[i][j]);
                }
            }
        }
        else if (choice == 2)
        {
            printf("Enter dimentions of the first Matrix: ");
            fflush(stdout); // flush after printing so it appeares before input.
            scanf("%d %d", &rows1, &col1);

            // building the 2d array.
            matrix1 = (int **)malloc(rows1 * sizeof(int *));
            for (int i = 0; i < rows1; i++)
                matrix1[i] = (int *)malloc(col1 * sizeof(int));

            // taking array elements as input.
            printf("Enter elements of the matrix: \n");
            fflush(stdout);
            for (size_t i = 0; i < rows1; i++)
            {
                for (size_t j = 0; j < col1; j++)
                {
                    scanf("%d", &matrix1[i][j]);
                }
            }
            printf("\n");

            printf("Enter dimentions of the second Matrix: ");
            fflush(stdout); // flush after printing so it appeares before input.
            scanf("%d %d", &rows2, &col2);

            // building the 2d array.
            matrix2 = (int **)malloc(rows2 * sizeof(int *));
            for (int i = 0; i < rows2; i++)
                matrix2[i] = (int *)malloc(col2 * sizeof(int));

            // taking array elements as input.
            printf("Enter elements of the matrix: \n");
            fflush(stdout);
            for (size_t i = 0; i < rows2; i++)
            {
                for (size_t j = 0; j < col2; j++)
                {
                    scanf("%d", &matrix2[i][j]);
                }
            }
            printf("\n");
        }

        rows3 = rows1;
        col3 = col2;
        // building the 2d array.
        matrix3 = (int **)malloc(rows3 * sizeof(int *));
        for (int i = 0; i < rows3; i++)
            matrix3[i] = (int *)malloc(col3 * sizeof(int));

        for (size_t i = 0; i < rows3; i++)
        {
            for (size_t j = 0; j < col3; j++)
            {
                matrix3[i][j] = 0;
            }
        }

        // sending rows and columns to all processes.
        for (size_t i = 1; i < size; i++)
        {
            MPI_Send(&rows1, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&col1, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&rows2, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&col2, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
        }

        // sending rows and columns to all processes.

        // because you can't send a 2d array in one go
        // you will send each individual row in the array.

        for (size_t i = 1; i < size; i++) // loops and sends to each other proccess.
        {
            for (size_t j = 0; j < rows1; j++) // loops through the rows of the 2d array and sends each row.
            {
                MPI_Send(matrix1[j], col1, MPI_INT, i, 0, MPI_COMM_WORLD); // send (1d array of current row, which has a size = number of columns).
            }
        }

        for (size_t i = 1; i < size; i++) // loops and sends to each other proccess.
        {
            for (size_t j = 0; j < rows2; j++) // loops through the rows of the 2d array and sends each row.
            {
                MPI_Send(matrix2[j], col2, MPI_INT, i, 0, MPI_COMM_WORLD); // send (1d array of current row, which has a size = number of columns).
            }
        }
    }
    else
    {

        // now each other process receives the values of rows and columns.
        MPI_Recv(&rows1, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(&col1, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(&rows2, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(&col2, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        // now you need to build the array again for each individual proccess.
        matrix1 = (int **)malloc(rows1 * sizeof(int *));
        for (int i = 0; i < rows1; i++)
            matrix1[i] = (int *)malloc(col1 * sizeof(int));

        // now we are going to receive the value of each row in the array in order to form the 2d array.
        for (size_t j = 0; j < rows1; j++)
        {
            MPI_Recv(matrix1[j], col1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        // now each other process receives the values of rows and columns.

        // now you need to build the array again for each individual proccess.
        matrix2 = (int **)malloc(rows2 * sizeof(int *));
        for (int i = 0; i < rows2; i++)
            matrix2[i] = (int *)malloc(col2 * sizeof(int));

        // now we are going to receive the value of each row in the array in order to form the 2d array.
        for (size_t j = 0; j < rows2; j++)
        {
            MPI_Recv(matrix2[j], col2, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        rows3 = rows1;
        col3 = col2;
        // building the 2d array.
        matrix3 = (int **)malloc(rows3 * sizeof(int *));
        for (int i = 0; i < rows3; i++)
            matrix3[i] = (int *)malloc(col3 * sizeof(int));

        for (size_t i = 0; i < rows3; i++)
        {
            for (size_t j = 0; j < col3; j++)
            {
                matrix3[i][j] = 0;
            }
        }
    }

    int rowsPerProcess = rows3 / size;
    int startingRow = rank * rowsPerProcess;
    int endingRow = startingRow + rowsPerProcess;
    int leftRows = rows3 % size;
    if (rank + 1 == size)
    {
        endingRow += leftRows;
    }

    for (int i = startingRow; i < endingRow; i++)
        for (int j = 0; j < col2; j++)
        {
            for (int k = 0; k < col1; k++)
                matrix3[i][j] += matrix1[i][k] * matrix2[k][j];
        }

    if (rank == 0)
    {
        int s, e;
        for (int i = 1; i < size; i++)
        {
            MPI_Recv(&s, 1, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            MPI_Recv(&e, 1, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            for (int j = s; j < e; j++)
            {
                MPI_Recv(matrix3[j], col3, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            }
        }
        print_matrix(matrix3, rows3, col3);
    }
    else
    {
        MPI_Send(&startingRow, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(&endingRow, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        for (int i = startingRow; i < endingRow; i++)
        {
            MPI_Send(matrix3[i], col3, MPI_INT, 0, 0, MPI_COMM_WORLD);
        }
    }

    // Finalize the MPI environment.
    MPI_Finalize();
    return 0;
}
