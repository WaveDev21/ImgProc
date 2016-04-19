#pragma version(1)
#pragma rs java_package_name(com.androidrecipes.imageprocessing)

rs_allocation input;
float filter [9];

void root(const uchar4* v_in, uchar4* v_out, const void* usrData, uint32_t x, uint32_t y)
{

    if(((x-1 < 0) && (y-1 < 0)) || (x-1 < 0) || (y-1 < 0)){
        *v_out = *v_in;
    }
    else if(((x+1 > rsAllocationGetDimX(input)) && (y+1 > rsAllocationGetDimY(input))) || (x+1 > rsAllocationGetDimX(input)) || (y+1 > rsAllocationGetDimY(input)))
    {
        *v_out = *v_in;
    }
    else
    {
        int a = 0;

        float4 array[9];
        int i, j;
        for(i = -1; i < 2; i=i+1 )
        {
            for(j = -1; j < 2; j = j+1)
            {
               uchar4 neighbor = rsGetElementAt_uchar4(input, x+j, y+i);
               array[a] = rsUnpackColor8888(neighbor);
               a = a + 1;
            }
        }

        float4 sum = 0;

        for(a = 0; a < 9; a=a+1 )
        {
            sum = sum + (array[a] * filter[a]);
        }

        *v_out = rsPackColorTo8888(sum);

    }
}