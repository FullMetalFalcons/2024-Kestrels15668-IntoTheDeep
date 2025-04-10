package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class SuckerTesting extends LinearOpMode {
    //Declare Servos for testing (idk man)
    CRServo LeftIntake, RightIntake;


    @Override
    public void runOpMode() {
        //Declare stuff
        LeftIntake = hardwareMap.crservo.get("Left_Intake");
        RightIntake = hardwareMap.crservo.get("Right_Intake");

        waitForStart();

        while(opModeIsActive()) {
            if (gamepad1.dpad_left) {
                LeftIntake.setPower(1);
            }

            if (gamepad1.dpad_right) {
                RightIntake.setPower(-1);
            }
        }
    }


}
