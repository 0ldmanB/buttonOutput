package com.example.android.buttonoutput

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


/* Below are attribute data classes, storing information unique to each 'square' of our gardening grid
* */
    val grid0_Attr : GridAttr = GridAttr("000",false)
    val grid1_Attr: GridAttr = GridAttr("001", false)
    val grid2_Attr: GridAttr = GridAttr("002", false)
    val grid3_Attr: GridAttr = GridAttr("003", false)
    val grid4_Attr: GridAttr = GridAttr("004", false)
    val grid5_Attr: GridAttr = GridAttr("005", false)

    val bed1 = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * Below are 6 button listeners, which will ultimately be initialized by a for loop function
         */


        grid0.setOnClickListener{
            gridClick(grid0,grid0_Attr)

        }

        grid1.setOnClickListener{
            gridClick(grid1,grid1_Attr)

        }

        grid2.setOnClickListener{
            gridClick(grid2,grid2_Attr)

        }

        grid3.setOnClickListener{
            gridClick(grid3,grid3_Attr)

        }

        grid4.setOnClickListener{
            gridClick(grid4,grid4_Attr)

        }

        grid5.setOnClickListener{
            gridClick(grid5,grid5_Attr)

        }

        program.setOnClickListener{
           programStringCreation()
        }



    }


        //Test function for creating the string used by the ESP32 to program the turret
    fun programStringCreation() {
        val bed0 = mutableListOf("000","001","007","008")   //Three example 'beds', each with a set of id#s, indicating which squares are part of the bed
        val bed1 = mutableListOf("015","016","021","022")   //ESP sees the id, moves the stepper to the position associated with that id (and monitors flow)
        val bed2 = mutableListOf("031","032","033")


        val bedDur = mutableListOf("120","240","100")      //This is the duration that each square in each bed must be watered in seconds. Eg: bedDur[0] = bed0, watered for 120seconds
        val bedList = mutableListOf(bed0,bed1,bed2)       //After each bed is created and confirmed, it is loaded into this master list of beds (determining overall bed watering order)

        var finalProgram : String = "$"     //Start final program string with $ (string start character)


        for((index,bed) in bedList.withIndex()){    //For loop creates program string, starting with squareid and adding spray duration, followed by a ',' to indicate a new command

            for(square in bed)
            {
                finalProgram += square
                finalProgram += bedDur[index].toString()
                finalProgram += ","
            }
        }


        println(finalProgram)


    }

    /*
      gridClick function is run whenever a 'square' is clicked. Currently it changes the stored click state (clicked or unclicked) and changes
      its background color.

     */

    fun gridClick(grid : Button,gridAttr : GridAttr)
    {
        if(gridAttr.isClicked == false ){
            grid.setBackgroundColor(Color.rgb(120,255,255))
            gridAttr.isClicked = !gridAttr.isClicked
            bed1.add(gridAttr.id)
        }
        else{
            grid.setBackgroundColor(Color.rgb(224,224,224))
            gridAttr.isClicked = !gridAttr.isClicked
            bed1.remove(gridAttr.id)
        }

    }



}

data class GridAttr(val id: String,var isClicked : Boolean = false)   //Data class for squares, will contain all important square data

/* Test code, not ready to implement
fun initGridAttr(val numberOfGrids: Int){
    for(i in 0..numberOfGrids)
    {

    }
}*/