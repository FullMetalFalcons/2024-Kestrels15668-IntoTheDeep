package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

public class KestrelArm {

    private DcMotorEx Tower, Slide, Arm;
    private Servo Claw;

    public KestrelArm(HardwareMap hardwareMap, Telemetry telemetry) {
        Tower = (DcMotorEx) hardwareMap.dcMotor.get("lift");
        Slide = (DcMotorEx) hardwareMap.dcMotor.get("slide");
        Arm = (DcMotorEx) hardwareMap.dcMotor.get("pivot");

        Claw = (Servo) hardwareMap.servo.get("claw");

        Tower.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        Tower.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        Tower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setClawPosition(double position) {
        Claw.setPosition(position);
    }

    public static class WaitAction implements Action {
        private final long waitTimeNs;
        private long startTimeNs;
        private boolean started = false;

        public WaitAction(double seconds) {
            this.waitTimeNs = TimeUnit.MILLISECONDS.toNanos((long) (seconds * 1000));
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!started) {
                startTimeNs = System.nanoTime();
                started = true;
            }
            return System.nanoTime() - startTimeNs < waitTimeNs;
        }
    }

    public static class ArmRotatorToPosition implements Action {
        private final int rotatorTarget;
        private final KestrelArm arm;
        private final long timeLimitNs;
        private long startTimeNs = 0;
        private boolean started = false;

        public ArmRotatorToPosition(KestrelArm arm, int rotTarget, double seconds) {
            this.arm = arm;
            this.rotatorTarget = rotTarget;
            this.timeLimitNs = TimeUnit.MILLISECONDS.toNanos((long) (seconds * 1000));
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!started) {
                startTimeNs = System.nanoTime();
                started = true;
                arm.Arm.setTargetPosition(rotatorTarget);
                arm.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.Arm.setPower(1);
            }

            // Continue running while the arm is moving & within time limit
            return arm.Arm.isBusy() && (System.nanoTime() - startTimeNs < timeLimitNs);
        }
    }
}
