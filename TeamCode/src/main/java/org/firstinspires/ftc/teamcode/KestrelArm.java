package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

public class KestrelArm {

    public DcMotorEx Tower, Slide, Arm;
    public static Servo Claw, ClawRotator;

    public KestrelArm(HardwareMap hardwareMap, Telemetry telemetry) {
        Tower = (DcMotorEx) hardwareMap.dcMotor.get("lift");
        Slide = (DcMotorEx) hardwareMap.dcMotor.get("slide");
        Arm = (DcMotorEx) hardwareMap.dcMotor.get("pivot");

        //Slide.setDirection(DcMotorSimple.Direction.REVERSE);

        Claw = (Servo) hardwareMap.servo.get("claw");
        ClawRotator = (Servo) hardwareMap.servo.get("ClawRotator");

        // Set Zero Power Behavior
        Tower.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Reset Encoders
        Tower.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Slide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Set Run Mode
        Tower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // ====== Power Control Methods (For Manual Control) ======

    public void setLiftPower(double power) {
        Tower.setPower(power); // Move lift up/down
    }

    public void setSlidePower(double power) {
        Slide.setPower(power); // Move slide in/out
    }

    // ====== Position Control Actions (For Autonomous) ======

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
            return arm.Arm.isBusy() /*&& (System.nanoTime() - startTimeNs < timeLimitNs)*/;
        }
    }

    public static class ClawAction implements Action {
        private final KestrelArm arm;
        private final double position;

        public ClawAction(KestrelArm arm, double position) {
            this.arm = arm;
            this.position = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.Claw.setPosition(position);
            return false; // This action runs instantly
        }
    }

    // ====== NEW: Lift & Slide Position Control ======

    public static class LiftToPosition implements Action {
        private final int targetPosition;
        private final KestrelArm arm;
        private final long timeLimitNs;
        private long startTimeNs = 0;
        private boolean started = false;

        public LiftToPosition(KestrelArm arm, int target, double seconds) {
            this.arm = arm;
            this.targetPosition = target;
            this.timeLimitNs = TimeUnit.MILLISECONDS.toNanos((long) (seconds * 1000));
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!started) {
                startTimeNs = System.nanoTime();
                started = true;
                arm.Tower.setTargetPosition(targetPosition);
                arm.Tower.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.Tower.setPower(1);
            }
            return arm.Tower.isBusy() && (System.nanoTime() - startTimeNs < timeLimitNs);
        }
    }

    public static class SlideToPosition implements Action {
        private final int targetPosition;
        private final KestrelArm arm;
        private final long timeLimitNs;
        private long startTimeNs = 0;
        private boolean started = false;

        public SlideToPosition(KestrelArm arm, int target, double seconds) {
            this.arm = arm;
            this.targetPosition = target;
            this.timeLimitNs = TimeUnit.MILLISECONDS.toNanos((long) (seconds * 1000));
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!started) {
                startTimeNs = System.nanoTime();
                started = true;
                arm.Slide.setTargetPosition(targetPosition);
                arm.Slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.Slide.setPower(1);
            }
            if (arm.Slide.isBusy() /*&& (System.nanoTime() - startTimeNs < timeLimitNs)*/) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
