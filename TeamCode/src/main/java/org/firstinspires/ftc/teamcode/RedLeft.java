package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.internal.camera.WebcamExample;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@Autonomous
public class RedLeft extends LinearOpMode {

    DcMotor backRight;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor arm;
    Servo leftServo;
    Servo rightServo;

    double horizontalPos = -100000;

    double threshold1 = 200;
    double threshold2 = 400;

    TfodProcessor tfod;
    VisionPortal visionPortal;

    double confidence = 0;


    int numRecognitions = 0;

    String[] LABELS = {"blueGP"};


    public void runOpMode() {
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        arm = hardwareMap.dcMotor.get("arm");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        initTfod();

        while (!opModeIsActive()) {
            scanForObjects();
        }


        waitForStart();

//middle spike
        if(horizontalPos < threshold2){
            drive(1,300,0,0);
            leftServo.setPosition(3);


        }
//Left spike
        else if(horizontalPos < threshold1){

        }
//right spike
        else if(horizontalPos > threshold2){

        }

        //arm.setPower(.5);
        //sleep(50);

//actual code
        drive(1, 2500, 0, 0);
        drive(1, 0, 4500, 0);


    }

    public void drive(double power, int forward, int strafe, int turn) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);

        backRight.setTargetPosition(-forward - strafe + turn);
        frontRight.setTargetPosition(-forward + strafe + turn);
        backLeft.setTargetPosition(-forward + strafe - turn);
        frontLeft.setTargetPosition(-forward - strafe - turn);

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

                .setModelFileName("blueGP")

                .setMaxNumRecognitions(1)
                .setTrackerMaxOverlap(0.25f)
                .setModelLabels(LABELS)
                .setNumDetectorThreads(1)
                .setNumExecutorThreads(1)

                .build();
        VisionPortal.Builder builder = new VisionPortal.Builder();


        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));


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


        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {

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