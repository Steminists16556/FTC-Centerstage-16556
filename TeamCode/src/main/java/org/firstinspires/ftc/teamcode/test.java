package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Autonomous
public class test extends LinearOpMode {
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor arm;
    DcMotor slider;
    DcMotor upLeft;
    DcMotor upRight;
    Servo leftServo;
    Servo rightServo;

    public void runOpMode(){

        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        upRight = hardwareMap.dcMotor.get("upRight");
        upLeft = hardwareMap.dcMotor.get("upLeft");
        slider = hardwareMap.dcMotor.get("slider");
        arm = hardwareMap.dcMotor.get("arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightServo = hardwareMap.servo.get("rightServo");
        leftServo = hardwareMap.servo.get("leftServo");

        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
//right spike
        //leftServo.setPosition(.5);
        //rightServo.setPosition(.5);

        //upLeft.setPower(-.5);
        //sleep(30);
        //upRight.setPower(.5);
        //sleep(30);


        //drive(1,1100,0,0);
        //drive(1,0,0,500);
        //sleep(50);
        //rightServo.setPosition(.2);
        //leftServo.setPosition(.8);

//Left spike
        leftServo.setPosition(.5);
        rightServo.setPosition(.5);

        upLeft.setPower(-.5);
        sleep(50);
        upRight.setPower(.5);
        sleep(50);


        drive(1,1300,0,0);
        drive(1,0,100,0);
        drive(1,0,0,-600);
        sleep(50);
        rightServo.setPosition(.2);
        leftServo.setPosition(.8);
        sleep(50);
        //drive(1,0,0,600);
       // drive(1,-300,0,0);
      //  drive(1,0,1300,0);

//Middle spike
        //leftServo.setPosition(.5);
        //rightServo.setPosition(.5);
        //upLeft.setPower(-.5);
        //sleep(50);
        //upRight.setPower(.5);
        //sleep(50);


        //drive(1,1400,0,0);
        //sleep(50);
        //rightServo.setPosition(.2);
        //leftServo.setPosition(.8);
//for when the robot is nearest to the backboard
        //drive(1,-400,0,0);
        //drive(1,0,1400,0);

    }

    public void drive(double power, int forward, int strafe, int turn) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setPower(.6 * power);
        backRight.setPower(.6*power);
        frontLeft.setPower(.6*power);
        backLeft.setPower(.6*power);

        backRight.setTargetPosition(-forward + strafe + turn);
        frontRight.setTargetPosition(-forward - strafe + turn);
        backLeft.setTargetPosition(-forward - strafe - turn);
        frontLeft.setTargetPosition(-forward + strafe - turn);

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy()) {

        }
        sleep(100);
    }
}
