package org.firstinspires.ftc.teamcode;

// RoadRunner Specific Imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
  Wireless Code Download: Terminal --> "adb connect 192.168.43.1:5555"
 */

@Config
@Autonomous
public class TrevorAuto extends LinearOpMode {
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));
        KestrelArm arm = new KestrelArm(hardwareMap, telemetry);
        Action moveToSpike1;
        Action moveToSpike2;
        Action moveToBasket0;
        Action moveToBasket1;
        Action moveToBasket2;

        moveToSpike1 = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(-14.5,19.5))
                .build();
        moveToSpike2 = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(-25,19.5))
                .build();
        moveToBasket0 = drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-16.5,7))
                .build();
        moveToBasket1 = drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-16.5,7))
                .build();
        moveToBasket2 = drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-16.5,7))
                .build();


        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                    arm.wristToPosition(0.229),
                    arm.armToPosition(0,0,500),
                    arm.wristToPosition(0.280),

                    moveToBasket0,
                    arm.armToPosition(70,0,1000),
                    arm.armToPosition(70,22,20),
                    arm.setIntake(arm.INTAKE_OUT),
                    new SleepAction(0.5),
                    arm.setIntake(0),
                    arm.armToPosition(70,0,1000),
                    arm.armToPosition(0,0,4000),

                    moveToSpike1,
                    arm.armToPosition(-5,8,20),
                    arm.setIntake(arm.INTAKE_IN),
                    arm.armToPosition(-15,8,10000),
                    new SleepAction(.75),
                    arm.setIntake(0),
                    arm.armToPosition(0,0,100),

                    moveToBasket1,
                    arm.armToPosition(70,0,1000),
                    arm.armToPosition(70,22,20),
                    arm.setIntake(arm.INTAKE_OUT),
                    new SleepAction(0.5),
                    arm.setIntake(0),
                    arm.armToPosition(70,0,2500),
                    arm.armToPosition(0,0,4000),


                    moveToSpike2,
                    arm.armToPosition(-5,8,20),
                    arm.setIntake(arm.INTAKE_IN),
                    arm.armToPosition(-15,8,10000),
                    new SleepAction(.75),
                    arm.setIntake(0),
                    arm.armToPosition(0,0,100),

                    moveToBasket2,
                    arm.armToPosition(70,0,2500),
                    arm.armToPosition(70,22,20),
                    arm.setIntake(arm.INTAKE_OUT),
                    new SleepAction(0.5),
                    arm.setIntake(0),
                    arm.armToPosition(70,0,2500),
                    arm.armToPosition(0,0,3000)

                    /*new SleepAction(1),
                    arm.wristToPosition(0.229),
                    arm.armToPosition(0,0,5)
                     */
                )
        );
    }
}