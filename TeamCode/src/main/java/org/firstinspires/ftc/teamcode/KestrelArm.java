package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

public class KestrelArm {

    public DcMotorEx motorArm, motorSlide;
    public Servo servoWrist;
    public CRServo servoWheel1, servoWheel2;

    public final int INTAKE_IN = -1;
    public final int INTAKE_OUT = 1;
    public final double WRIST_OUT = .317;
    public final double WRIST_IN = .280;


    public KestrelArm(HardwareMap hardwareMap, Telemetry telemetry) {
        motorSlide = (DcMotorEx) hardwareMap.dcMotor.get("Slide");
        motorArm = (DcMotorEx) hardwareMap.dcMotor.get("Wormgear");

        //Slide.setDirection(DcMotorSimple.Direction.REVERSE);

        servoWrist = (Servo) hardwareMap.servo.get("Wrist");
        servoWheel1 = (CRServo) hardwareMap.crservo.get("Wheel1");
        servoWheel2 = (CRServo) hardwareMap.crservo.get("Wheel2");

        // Set Zero Power Behavior
        motorArm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Reset Encoders
        motorArm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Set Run Mode
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Power Control Methods (For Manual Control, used for mainly testing)

    public class ArmSlideToPosition implements Action {
        // Use constructor parameter to set target position
        private int targetArmPositionTicks;
        private int targetSlidePositionTicks;
        private int endErrorTicks;
        public ArmSlideToPosition(int armPosTicks, int slidePosTicks, int errorToEndTicks) {
            super();
            // Convert target degrees to target ticks
            targetArmPositionTicks = (armPosTicks);

            // Convert target inches to target ticks
            targetSlidePositionTicks = (slidePosTicks);

            // Once our arm and slide have less error than this, the
            //   next action will begin (as the arm and slide fine
            //   tune themselves in parallel)
            endErrorTicks = errorToEndTicks;
        }

        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            if (!initialized) {
                motorArm.setTargetPosition(targetArmPositionTicks);
                motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                packet.put("Arm isBusy", motorArm.isBusy());

                motorSlide.setTargetPosition(targetSlidePositionTicks);
                motorSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                packet.put("Slide isBusy", motorSlide.isBusy());
                initialized = true;
            }


            // Find out how far each mechanism is from their desired positions
            int armErrorTicks = Math.abs(motorArm.getCurrentPosition() - motorArm.getTargetPosition());
            int slideErrorTicks = Math.abs(motorSlide.getCurrentPosition() - motorSlide.getTargetPosition());

            //TODO Add limit checks
            if ((armErrorTicks > endErrorTicks && motorArm.isBusy()) || (slideErrorTicks > endErrorTicks && motorSlide.isBusy())) {
                // Returning true will run the action again
                motorArm.setPower(1);
                motorSlide.setPower(1);
                return true;
            } else {
                // Returning false will end the action
                //Arm.setPower(0);
                //Slide.setPower(0);
                return false;
            }
        }
    }
    public ArmSlideToPosition armToPosition(int targetArmTicks, int targetSlideTicks, int errorToEndTicks) {
        return new ArmSlideToPosition(targetArmTicks, targetSlideTicks, errorToEndTicks);
    }
    public class SetIntake implements Action {
        // Use constructor parameter to set target position
        private int desiredIntakeSpeed;
        public SetIntake(int intakeSpeed) {
            super();
            desiredIntakeSpeed = intakeSpeed;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            servoWheel1.setPower(-desiredIntakeSpeed);
            servoWheel2.setPower(desiredIntakeSpeed);
            return false;
        }
    }
    public SetIntake setIntake(int intakeSpeed) { return new SetIntake(intakeSpeed); }


    public class WristToPosition implements Action {
        // Use constructor parameter to set target position
        private double targetWristPosition;
        public WristToPosition(double wristPos) {
            super();
            targetWristPosition = wristPos;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            servoWrist.setPosition(targetWristPosition);
            return false;
        }
    }
    public WristToPosition wristToPosition(double wristPos) { return new WristToPosition(wristPos); }

/*
    public void setSlidePower(double power) {
        motorSlide.setPower(power); // Move slide in/out
    }

    //Position Control Actions (For Auto)

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
        private boolean started = false;

        //Rotates Arm To Position
        public ArmRotatorToPosition(KestrelArm arm, int rotTarget) {
            this.arm = arm;
            this.rotatorTarget = rotTarget;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!started) {
                started = true;
                arm.motorArm.setTargetPosition(rotatorTarget);
                arm.motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.motorArm.setPower(1);
            }
            return arm.motorArm.isBusy();
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
            arm.servoWrist.setPosition(position);
            return false; // This action runs instantly
        }
    }

    //Lift & Slide Position Control

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
                arm.motorSlide.setTargetPosition(targetPosition);
                arm.motorSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.motorSlide.setPower(1);
            }
            if (arm.motorSlide.isBusy() && (System.nanoTime() - startTimeNs < timeLimitNs)) {
                return true;
            }
            else {
                return false;
            }
        }
    }
 */
}
