package net.unkleacid.voidcalls.feature;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class AdminspaceMazeFeature extends Feature {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        System.out.println(x + " " + y + " " + z);

//        function generateCel(x, z) {
//
//            for all 4 walls of this cel {
//
//                step outside of the wall
//
//                if that area isn't generated yet {
//                    stop
//                } else {
//
//                    wander around randomly for 10 steps
//
//                    if at any point we step back into the cel at x,z {
//                        don't place a door on this wall
//                    } else {
//                        place a door on this wall
//                    }
//                }
//            }
//        }

//        Voidcalls.ADMINSPACE_LIGHT_BLOCK.id
//        Voidcalls.ADMINSPACE_BLOCK.id

        return true;
    }
}
