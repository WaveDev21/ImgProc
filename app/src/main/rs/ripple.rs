#pragma version(1)
#pragma rs java_package_name(com.androidrecipes.imageprocessing)

float centerX;
float centerY;
float minRadius;

float scalar;
float dampler;
float frequency;

void root(const uchar4* v_in, uchar4* v_out, const void* usrData, uint32_t x, uint32_t y)
{
    float dx = x - centerX;
    float dy = y - centerY;
    float radius = sqrt(dx*dx + dy*dy);

    if(radius < minRadius)
    {
        *v_out = *v_in;
    }
    else
    {
        float4 f4 = rsUnpackColor8888(*v_in);
        float shiftedRadius = radius - minRadius;

        float multiplier = (scalar * exp(-shiftedRadius * dampler) * -sin(shiftedRadius*frequency)) + 1;

        float3 transformed = f4.rgb * multiplier;
        *v_out = rsPackColorTo8888(transformed);
    }
}