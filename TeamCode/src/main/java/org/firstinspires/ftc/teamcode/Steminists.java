package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

@TeleOp(name="Steminists")
public class Steminists extends OpMode { //1st bracket

    //hardware declarations
    DcMotor backRight;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor frontLeft;
    DcMotor slider;
    DcMotor arm;
    DcMotor upLeft;
    DcMotor upRight;
    Servo rightServo;
    Servo leftServo;
    Servo drone;

    double hangTicks = 1425.1;
    double hangTarget;



    public void init() { //2nd bracket

//hardware mapping for chassis motors
        backRight = hardwareMap.dcMotor.get("backRight");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        upRight = hardwareMap.dcMotor.get("upRight");
        upLeft = hardwareMap.dcMotor.get("upLeft");

//hardware mapping for manipulator motors and a brake
        slider = hardwareMap.dcMotor.get("slider");
        //slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm = hardwareMap.dcMotor.get("arm");
        arm.setDirection((DcMotor.Direction.REVERSE));
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//hardware mapping for claw servos
        rightServo =  hardwareMap.servo.get("rightServo");
        leftServo =  hardwareMap.servo.get("leftServo");

        drone = hardwareMap.servo.get("drone");

//reversing right motors
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //upRight.setDirection(DcMotorSimple.Direction.REVERSE);


    } //2nd end bracket

    public void loop() { //start loop

//code for chassis
        double forward = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        double up = .5 * gamepad2.left_stick_y;
        double out = .6 * gamepad2.right_stick_y;
        double lift = gamepad1.right_stick_y;

        backRight.setPower(forward - strafe + turn);
        frontRight.setPower(forward + strafe + turn);
        backLeft.setPower(forward + strafe - turn);
        frontLeft.setPower(forward - strafe - turn);

//code for manipulator arm
        arm.setPower(up);

        slider.setPower(out);
//code for suspender
        //upLeft.setPower(lift);
        //upRight.setPower(lift);


//Servo code
        if (gamepad2.left_bumper) {
            rightServo.setPosition(.2);
            leftServo.setPosition(.8);

        }

        if (gamepad2.right_bumper) {
            rightServo.setPosition(.5);
            leftServo.setPosition(.5);

        }
//Drone code
        if (gamepad2.x) {
            drone.setPosition(.6);

        }
        if (gamepad2.y) {
            drone.setPosition(.2);
        }
//hanging ticks

        //all the way
        if(gamepad1.a) {
            full(-1.80);
            full2(1.80);
        }

        //origin
        if(gamepad1.x) {
            full(-.4);
            full2(.4);
        }

        //latch on
        if(gamepad1.y) {
            full(-1.70);
            full2(1.70);
        }

        //hang
        if(gamepad1.b) {
            full(-.3);
            full2(.3);
        }




    } //end of loops

    public void full (double turnage) {
        hangTarget = hangTicks*turnage;
        upLeft.setTargetPosition((int)hangTarget);
        //upRight.setTargetPosition((int)hangTarget);

        upLeft.setPower(.75);
        //upRight.setPower(-.75);

        upLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //upRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void full2 (double turnage) {
        hangTarget = hangTicks*turnage;
        upRight.setTargetPosition((int)hangTarget);
        upRight.setPower(.75);
        upRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

/*
    public void origin(){
        upLeft.setTargetPosition(0);
        upRight.setTargetPosition(0);

        upLeft.setPower(.75);
        upRight.setPower(.75);

        upLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        upRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
*/

}    //last bracket

