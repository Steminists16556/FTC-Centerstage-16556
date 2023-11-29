package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class RedRight extends LinearOpMode {

//hardware declarations
    DcMotor backRight ;
    DcMotor backLeft ;
    DcMotor frontRight ;
    DcMotor frontLeft ;
    //Servo rightServo;
    //Servo leftServo;
    //DcMotor arm;



    public void runOpMode(){

//hardware mappings
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        //arm = hardwareMap.dcMotor.get("arm");
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        //arm.setPower(.5);
        //sleep(50);

//actual code
        drive(1, 300, 0,0);
        drive(1, 0, 2000, 0);

        //rightServo.setPosition(.2);
        //leftServo.setPosition(1);









    }

    public void drive(double power, int forward, int strafe, int turn){
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);

        backRight.setTargetPosition(-forward-strafe+turn);
        frontRight.setTargetPosition(-forward+strafe+turn);
        backLeft.setTargetPosition(-forward+strafe-turn);
        frontLeft.setTargetPosition(-forward-strafe-turn);

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy()){

        }

        sleep(100);


    }


}
