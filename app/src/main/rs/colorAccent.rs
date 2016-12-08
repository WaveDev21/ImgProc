#pragma version(1)
#pragma rs java_package_name(com.example.wave.androidimageprocessingjava)
#pragma rs_fp_relaxed


uchar4 RS_KERNEL colorAccent(uchar4 in)
{
    float hsrc, ssrc, vsrc, hdst, sdst, vdst, hfrom, hto;
    float x, f, i;
    int range = 250;

    uchar4 out = in;
    float red, grn, blu, val, hue, sat;

    red = 190.0/255.0;
    grn = 20.0/255.0;
    blu = 0.0/255.0;

    x = fmin(fmin(red, grn), blu);
    vsrc = fmax(fmax(red, grn), blu);
    if(x == val){
        hsrc = 0;
        ssrc = 0;
    }else{
        f = (red == x) ? grn - blu : ((grn == x) ? blu - red : red - grn);
        i = (red == x) ? 3 : ((grn == x) ? 5 : 1);
        hsrc = fmod((i - f/(val - x)) * 60, 360);
        ssrc = ((vsrc - x)/vsrc);
    }


    hfrom = fmod((hsrc - (range / 2)) + 360, 360);
    hto = fmod(hsrc + (range / 2), 360);

    red = in.r/255.0;
    grn = in.g/255.0;
    blu = in.b/255.0;

    x = fmin(fmin(red, grn), blu);
    vdst = fmax(fmax(red, grn), blu);
    if(x == val){
        hdst = 0;
        sdst = 0;
    }else{
        f = (red == x) ? grn - blu : ((grn == x) ? blu - red : red - grn);
        i = (red == x) ? 3 : ((grn == x) ? 5 : 1);
        hdst = fmod((i - f/(vdst - x)) * 60, 360);
        sdst = ((vdst - x)/vdst);
    }

    if (( hfrom <= hto &&
       hfrom <= hdst &&
       hto >= hdst) ||
       ( hfrom > hto &&
       ( hfrom <= hdst ||
       hto >= hdst ))) {

    }else{
        out.r = (int) (0.299 * in.r + 0.587 * in.g + 0.114 * in.b);
        out.g = (int) (0.299 * in.r + 0.587 * in.g + 0.114 * in.b);
        out.b = (int) (0.299 * in.r + 0.587 * in.g + 0.114 * in.b);
    }

    return out;
}
