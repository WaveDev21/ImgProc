#pragma version(1)
#pragma rs java_package_name(com.example.wave.androidimageprocessingjava)
#pragma rs_fp_relaxed

int range;
float startRed;
float startGreen;
float startBlue;

uchar4 RS_KERNEL colorAccent(uchar4 in)
{
    float hSource, sSource, vSource, hDest, sDest, vDest, hFrom, hTo;
    float x, f, i;

    uchar4 out = in;
    float red, green, blue, val, hue, sat;

    red = startRed/255.0;
    green = startGreen/255.0;
    blue = startBlue/255.0;

    x = fmin(fmin(red, green), blue);
    vSource = fmax(fmax(red, green), blue);
    if(x == vSource){
        hSource = 0;
        sSource = 0;
    }else{

        if(red == x){
            f = green - blue;
        }else{
            if(green == x){
                f = blue - red;
            }else{
                f = red - green;
            }
        }

        if(red == x){
            i = 3;
        }else{
            if(green == x){
                i = 5;
            }else{
                i = 1;
            }
        }
        hSource = fmod((i-f/(vSource-x)) * 60, 360);
    }


    hFrom = fmod(hSource - (range / 2) + 360, 360);
    hTo = fmod(hSource + (range / 2) + 360, 360);

    red = in.r/255.0;
    green = in.g/255.0;
    blue = in.b/255.0;

    x = fmin(fmin(red, green), blue);
    vDest = fmax(fmax(red, green), blue);
    if(x == vDest){
        hDest = 0;
    }else{

        if(red == x){
            f = green - blue;
        }else{
            if(green == x){
                f = blue - red;
            }else{
                f = red - green;
            }
        }

        if(red == x){
            i = 3;
        }else{
            if(green == x){
                i = 5;
            }else{
                i = 1;
            }
        }
        hDest = fmod((i-f/(vDest-x)) * 60, 360);
    }

    if (( hFrom <= hTo &&
       hFrom <= hDest &&
       hTo >= hDest) ||
       ( hFrom > hTo &&
       ( hFrom <= hDest ||
       hTo >= hDest ))) {

    }else{
        out.r = (int) (0.299 * in.r + 0.587 * in.g + 0.114 * in.b);
        out.g = (int) (0.299 * in.r + 0.587 * in.g + 0.114 * in.b);
        out.b = (int) (0.299 * in.r + 0.587 * in.g + 0.114 * in.b);
    }

    return out;
}
