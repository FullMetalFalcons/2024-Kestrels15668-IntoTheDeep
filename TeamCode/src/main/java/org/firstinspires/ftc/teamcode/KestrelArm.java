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

    public KestrelArm(HardwareMap hardwareMap, Telemetry telemetry)
    {
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

    public class ArmRotatorToPosition implements Action {
        private int rotatorPower;
        private boolean rotInit = false;
        private long startTimeNs;
        private double seconds;

        public ArmRotatorToPosition(int rotPower, double time) {
            super();
            seconds = time;
            rotatorPower = rotPower;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!rotInit) {
                startTimeNs = System.nanoTime();
                rotInit = true;
            }
            if (System.nanoTime() - startTimeNs > TimeUnit.MILLISECONDS.toNanos((long) (seconds * 1000))) {
                Arm.setTargetPosition(rotatorPower);
                Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                Arm.setPower(1);
                return false;
            }
            else {
                return true;
            }
        }

        public ArmRotatorToPosition armRotatorToPosition(int rotatorPower, double seconds)
        {
            return new ArmRotatorToPosition(rotatorPower, seconds);
        }

    }

    public abstract class Wait implements Action
    {
        private double seconds;

    }
}
