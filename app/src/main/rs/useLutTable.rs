#pragma version(1)
#pragma rs java_package_name(com.example.wave.androidimageprocessingjava)
#pragma rs_fp_relaxed

float redLut [256];
float greenLut [256];
float blueLut [256];

uchar4 RS_KERNEL useLutTable(uchar4 in)
{
    uchar4 out = in;
    out.r = redLut[in.r];
    out.g = greenLut[in.g];
    out.b = blueLut[in.b];
    return out;
}