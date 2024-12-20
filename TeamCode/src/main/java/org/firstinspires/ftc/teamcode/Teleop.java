package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Teleop extends LinearOpMode {
    //Initialize motors, servos, sensors, imus, etc.
    DcMotorEx m1,m2,m3,m4, rotator, actuate, slide;

    Servo clawRotator, clawClaw;

    public void runOpMode() {
        //Define those motors and stuff
        //The string should be the name on the Driver Hub
        m1 = (DcMotorEx) hardwareMap.dcMotor.get("front_left");
        m2 = (DcMotorEx) hardwareMap.dcMotor.get("front_right");
        m3 = (DcMotorEx) hardwareMap.dcMotor.get("back_left");
        m4 = (DcMotorEx) hardwareMap.dcMotor.get("back_right");
        rotator = (DcMotorEx) hardwareMap.dcMotor.get("pivot");
        actuate = (DcMotorEx) hardwareMap.dcMotor.get("lift");
        slide = (DcMotorEx) hardwareMap.dcMotor.get("slide");

        //servos
        //clawRotator = hardwareMap.servo.get("clawRotator");
        clawClaw = hardwareMap.servo.get("claw");

        //Set them to the correct modes
        //This reverses the motor direction
        m1.setDirection(DcMotorSimple.Direction.REVERSE);
        m3.setDirection(DcMotorSimple.Direction.REVERSE);

        //This resets the encoder values when the code is initialized
        m1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m3.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        m4.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        //This makes the wheels tense up and stay in position when it is not moving, opposite is FLOAT
        m1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        m3.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rotator.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        //This lets you look at encoder values while the OpMode is active
        //If you have a STOP_AND_RESET_ENCODER, make sure to put this below it
        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        clawClaw.setPosition(.75);

        //All the code above this will begin when you press INIT on the Driver Hub
        //This waits until you press the play button
        waitForStart();

        //The code inside this loop will run once you press the play button
        //For TeleOp, we want the code to run continuously until you press stop
        //For auto, you would want the code to run once IF the play button is pressed
        while(opModeIsActive()) {
            //MecanumDrive: Left stick moves up/down/strafe, right stick turns
//Requires Mecanum wheels
            double px = gamepad1.left_stick_x;
            double py = -gamepad1.left_stick_y;
            double pa = -gamepad1.right_stick_x;
            boolean arrrrrrmUp = gamepad2.right_bumper;
            boolean arrrrrrmDown = gamepad2.left_bumper;
            boolean rotUp = gamepad2.a;
            boolean rotDown = gamepad2.b;
            boolean clawOpen = gamepad2.y;
            boolean rotatorclaw = gamepad2.x;


            double p1 = px + py - pa;
            double p2 = -px + py + pa;
            double p3 = -px + py - pa;
            double p4 = px + py + pa;
            double max = Math.max(1.0, Math.abs(p1));
            max = Math.max(max, Math.abs(p2));
            max = Math.max(max, Math.abs(p3));
            max = Math.max(max, Math.abs(p4));
            p1 /= max;
            p2 /= max;
            p3 /= max;
            p4 /= max;
            m1.setPower(p1);
            m2.setPower(p2);
            m3.setPower(p3);
            m4.setPower(p4);


            if (clawOpen)
            {
                clawClaw.setPosition(0);
            }
            else {
                clawClaw.setPosition(.75);
            }

            if (arrrrrrmUp)
            {
                slide.setPower(1);
            }
            else if (arrrrrrmDown)
            {
                slide.setPower(-1);
            }
            else {
                slide.setPower(0);
            }

            if (rotUp)
            {
                rotator.setPower(1);
            }
            else if (rotDown)
            {
                rotator.setPower(-1);
            }
            else {
                rotator.setPower(0);
            }
        }
    }
}