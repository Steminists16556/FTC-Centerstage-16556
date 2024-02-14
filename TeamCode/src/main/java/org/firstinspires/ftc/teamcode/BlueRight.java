package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous
public class BlueRight extends LinearOpMode  {
//farthest blue side
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



    double horizontalPos = -100000;
    double threshold1 = 200;
    //double threshold2 = 400;

    TfodProcessor tfod;
    VisionPortal visionPortal;

    double confidence = 0;

    int numRecognitions = 0;

    String[] LABELS = {"blueGP"};

    public void runOpMode(){

//hardware mapping
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


        initTfod();

        while (!opModeIsActive()){
            scanForObjects();
        }

        waitForStart();

//right spike
        if(horizontalPos == -100000 || confidence < .7){
            rightServo.setPosition(.5);
            leftServo.setPosition(.5);

            upLeft.setPower(-.5);
            sleep(50);
            upRight.setPower(.5);
            sleep(50);


            drive(1,1300,0,0);
            drive(1,0,-100,0);
            drive(1,0,0,600);
            sleep(50);
            rightServo.setPosition(.2);
            leftServo.setPosition(.8);
            slider.setPower(.5);
            sleep(50);
            drive(1,-100,0,0);

        }

//Left spike
        else if(horizontalPos < threshold1 || numRecognitions == 2){
            leftServo.setPosition(.5);
            rightServo.setPosition(.5);

            upLeft.setPower(-.5);
            sleep(50);
            upRight.setPower(.5);
            sleep(50);


            drive(1,1100,0,0);
            drive(1,0,200,0);
            drive(1,0,0,-500);
            sleep(50);
            rightServo.setPosition(.2);
            leftServo.setPosition(.8);
            slider.setPower(.5);
            sleep(50);
            drive(1,-100,0,0);

        }
//middle spike
        else if(horizontalPos > threshold1){
            rightServo.setPosition(.5);
            leftServo.setPosition(.5);

            upLeft.setPower(-.5);
            sleep(50);
            upRight.setPower(.5);
            sleep(50);



            drive(1,1300,0,0);
            sleep(50);
            rightServo.setPosition(.2);
            leftServo.setPosition(.8);
            slider.setPower(.5);
            sleep(50);
            drive(1,-400,0,0);
            //drive(1,0,1400,0);

        }



    }
    public void drive(double power, int forward, int strafe, int turn) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setPower(power*.6);
        backRight.setPower(power*.6);
        frontLeft.setPower(power*.6);
        backLeft.setPower(power*.6);

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
        sleep(300);


    }
    private void initTfod() {
        tfod = new TfodProcessor.Builder()

                .setModelFileName("blueGP.tflite")
                .setMaxNumRecognitions(3)
                .setTrackerMaxOverlap(0.25f)
                .setModelLabels(LABELS)
                .setNumDetectorThreads(4)
                .setNumExecutorThreads(4)

                .build();
        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(hardwareMap.get(WebcamName.class,"Webcam 1"));

        builder.addProcessor(tfod);
        visionPortal = builder.build();
    }

    private boolean scanForObjects() {
        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        numRecognitions = ((List<?>) currentRecognitions).size();

        if(currentRecognitions.isEmpty()){
            horizontalPos = -100000;
            return false;
        }

        for (Recognition recognition : currentRecognitions) {
            horizontalPos = (recognition.getLeft()+recognition.getRight())/2;
            confidence = recognition.getConfidence();

            telemetry.addData("", " ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f", horizontalPos);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            break;
        }

        return true;
    }

}
