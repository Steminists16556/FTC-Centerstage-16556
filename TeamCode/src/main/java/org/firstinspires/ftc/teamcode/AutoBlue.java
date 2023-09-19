package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class AutoBlue extends LinearOpMode {
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor arm;

    public void runOpMode() {
        //hardware mappings
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        arm = hardwareMap.dcMotor.get("arm");


        waitForStart();
        arm.setPower(.5);
        sleep(50);

        //actual code

        drive(1, 0, 0, 150);
        drive(1, 0, 2000, 0);
        //drive(1, 0, 1500, 0);

    }

    public void drive(double power, int pivot, int vertical, int horizontal) {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);

        backLeft.setTargetPosition(pivot - vertical + horizontal);
        backRight.setTargetPosition(pivot + vertical - horizontal);
        frontLeft.setTargetPosition(pivot - (vertical - horizontal));
        frontRight.setTargetPosition(pivot + (vertical + horizontal));

        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (backLeft.isBusy() && frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy()) {


        }
        sleep(100);

    }

}
