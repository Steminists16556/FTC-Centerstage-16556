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
    Servo rightServo;
    Servo leftServo;
    Servo drone;

    //declares the # of ticks
    //double sliderTicks = 1426.1;
    //double sliderTarget;


    public void init() { //2nd bracket

//hardware mapping for chassis motors
        backRight = hardwareMap.dcMotor.get("backRight");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");

//hardware mapping for manipulator motors and a brake
        slider = hardwareMap.dcMotor.get("slider");
        //slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        arm = hardwareMap.dcMotor.get("arm");
        arm.setDirection((DcMotor.Direction.REVERSE));
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//hardware mapping for claw servos
        rightServo =  hardwareMap.servo.get("rightServo");
        leftServo =  hardwareMap.servo.get("leftServo");

//reversing right motors
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

//encoders for slider

       // slider.setDirection(DcMotorSimple.Direction.REVERSE);
        //slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


//encoders for arm

       // arm.setDirection(DcMotorSimple.Direction.REVERSE);
       // arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    } //2nd end bracket

    public void loop() { //start loop

//code for chassis
        double forward = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        double up = .5 * gamepad2.left_stick_y;
        double out = .6 * gamepad2.right_stick_y;

        backRight.setPower(forward + strafe + turn);
        frontRight.setPower(forward - strafe + turn);
        backLeft.setPower(forward + strafe - turn);
        frontLeft.setPower(forward - strafe - turn);

//code for manipulator arm
        arm.setPower(up);

        slider.setPower(out);

//encoder mapping button start for arm/code for slider
       // if (gamepad2.y) {
            //full(1.45);


        //if (gamepad2.x) {
           // origin();


        //if (gamepad2.b) {
           // full(2.175);


        //if (gamepad2.a) {
            //full(0.725);


//Servo code
        if (gamepad2.left_bumper) {
            rightServo.setPosition(.2);
            leftServo.setPosition(1);

        }

        if (gamepad2.right_bumper) {
            rightServo.setPosition(.5);
            leftServo.setPosition(.5);

        }
//Drone code
        //if (gamepad2.x) {
           // drone.setPosition(1);



        //telemetry.addData("Slider ticks:", arm.getCurrentPosition());

    } //end of loops

    //ticks loop for slider start
    //public void full(double turnage) {
       //sliderTarget = sliderTicks * turnage;
        //slider.setTargetPosition((int) sliderTarget);
        //slider.setPower(.85);
        //slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    //public void origin() {
        //slider.setTargetPosition(0);
        //slider.setPower(0.9);
        //slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);



//ticks loop for slider ends


}    //last bracket

