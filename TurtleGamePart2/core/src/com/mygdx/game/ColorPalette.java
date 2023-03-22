package com.mygdx.game;

public interface ColorPalette
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    // RGB_VALUE
    // All colors in RGD; first inner-inner array index is the background; the rest are the colors.
    int[][][] colorsRGB = {
            // Canisius: White; Dark Blue; Yellow; Light Blue; Orange
            { { 255, 255, 255 }, { 13, 34, 64 }, { 255, 209, 0 }, { 1, 98, 187 }, { 255, 120, 0 } },

            // Neon: Black; Blue; Green; Pink; Yellow
            { { 0, 0, 0 }, { 0, 255, 234 }, { 6, 255, 0 }, { 255, 0, 240 }, { 255, 246, 0 } },

            // Moody: Light Pink; Dark Blue; Light Blue; Pink; Brown
            { { 249, 222, 201 }, { 58, 64, 90 }, { 174, 197, 235 }, { 233, 175, 163 }, { 104, 80, 68 } },

            // Greyscale: Grey; Black; Dark Grey; Light Grey; White
            { { 136, 136, 136 }, { 0, 0 ,0 }, { 79, 79, 79 }, { 185, 185, 185 }, { 255, 255, 255 } },

            // Muave: Dark Muave; Purple; Lavender; Blue; Light Blue
            { { 63, 37, 30 }, { 132, 47, 77 }, { 137, 115, 137 }, { 140, 178, 201 }, { 229, 239, 252 } },

            // USA: Dark Blue; White; Red; Light Blue; Light Red
            { { 22, 44, 115 }, { 255, 255, 255 }, { 228, 33, 33 }, { 119, 126, 215 }, { 208, 118, 118 } },

            // Pastel: White; Pink; Yellow; Green; Blue
            { { 255, 255, 255 }, { 247, 204, 204 }, { 243, 239, 135 }, { 178, 248, 183 }, { 185, 188, 247 } },

            // Flame: Dark Brown; Red; Orange; Yellow; White
            { { 40, 2, 2 }, { 255, 0, 0 }, { 255, 132, 0 }, { 255, 246, 0 }, { 255, 255, 255 } },

            // Valentine: Pastel Pink; White; Light Pink; Medium Pink; Dark Pink
            { { 255, 193, 193 }, { 255, 255, 255 }, { 255, 133, 133 }, { 288, 61, 100 }, { 178, 6, 75 } },

            // Citrus: White; Lemon; Orange; Lime; Grapefruit
            { { 255, 255, 255 }, { 252, 255, 21 }, { 252, 149, 65 }, { 108, 241, 73 }, { 240, 39, 60 } }
    };

    /////////////////////////////////////////////////////////////////////////////////

    // HEXADECIMAL_VALUE
    int[][] colorsHexa = {
            // Canisius: White; Dark Blue; Yellow; Light Blue; Orange
            { 0xffffffff, 0x0d2240ff, 0xffd100ff, 0x0162bbff, 0xff7800ff },

            // Neon: Black; Blue; Green; Pink; Yellow
            { 0x000000ff, 0x00ffeaff, 0x06ff00ff, 0xff00f0ff, 0xfff600ff },

            // Moody: Light Pink; Dark Blue; Light Blue; Pink; Brown
            { 0xf9dec9ff, 0x3a405aff, 0xaec5ebff, 0xe9afa3ff, 0x685044ff },

            // Greyscale: Grey; Black; Dark Grey; Light Grey; White
            { 0x888888ff, 0x000000ff, 0x4f4f4fff, 0xb9b9b9ff, 0xffffffff },

            // Muave: Dark Muave; Purple; Lavender; Blue; Light Blue
            { 0x3f251eff, 0x842f4dff, 0x897389ff, 0x8cb2c9ff, 0xe5effcff },

            // USA: Dark Blue; White; Red; Light Blue; Light Red
            { 0x162c73ff, 0xffffffff, 0xe42121ff, 0x777ed7ff, 0xd07676ff },

            // Pastel: White; Pink; Yellow; Green; Blue
            { 0xffffffff, 0xf7ccccff, 0xf3ef87ff, 0xb2f8b7ff, 0xb9bcf7ff },

            // Flame: Dark Brown; Red; Orange; Yellow; White
            { 0x280202ff, 0xff0000ff, 0xff8400ff, 0xfff600ff, 0xffffffff },

            // Valentine: Pastel Pink; White; Light Pink; Medium Pink; Dark Pink
            { 0xffc1c1ff, 0xffffffff, 0xff8585ff, 0xe43d64ff, 0xb2064bff },

            // Citrus: White; Lemon; Orange; Lime; Grapefruit
            { 0xffffffff, 0xfcff15ff, 0xfc9541ff, 0x6cf149ff, 0xf0273cff }
    };
}
