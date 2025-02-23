package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class LiftSlidePositionTest extends OpMode {
    private DcMotorEx Tower, Slide;

    @Override
    public void init() {
        Tower = hardwareMap.get(DcMotorEx.class, "lift");
        Slide = hardwareMap.get(DcMotorEx.class, "slide");

        Tower.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        Tower.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        Slide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        Tower.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            Tower.setTargetPosition(500);
            Tower.setPower(1);
        }
        if (gamepad1.b) {
            Tower.setTargetPosition(0);
            Tower.setPower(1);
        }
        if (gamepad1.x) {
            Slide.setTargetPosition(300);
            Slide.setPower(1);
        }
        if (gamepad1.y) {
            Slide.setTargetPosition(0);
            Slide.setPower(1);
        }

        telemetry.addData("Lift Pos", Tower.getCurrentPosition());
        telemetry.addData("Slide Pos", Slide.getCurrentPosition());
        telemetry.update();
    }
}
