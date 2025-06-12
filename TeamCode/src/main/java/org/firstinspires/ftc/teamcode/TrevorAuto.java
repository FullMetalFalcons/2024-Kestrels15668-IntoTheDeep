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
        Action moveToHome;
        Action moveToBasket;

        moveToSpike1 = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(-24,19.5))
                .strafeTo(new Vector2d(-14,19.5))
                .build();
        moveToSpike2 = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(-24,19.5))
                .build();
        moveToHome = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(0,0))
                .build();
        moveToBasket = drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-16.5,7))
                //.strafeToLinearHeading(new Vector2d(-16.5,7),Math.toRadians(135))
                .build();


        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                    arm.wristToPosition(0.229),
                    arm.armToPosition(0,0,10),
                    arm.wristToPosition(0.280),
                    new SleepAction(1),
                    moveToSpike1,
                    new SleepAction(1),
                    arm.armToPosition(0,8.5,5),
                    arm.setIntake(arm.INTAKE_IN),
                    arm.armToPosition(-10,8.5,10000),
                    new SleepAction(1),
                    arm.setIntake(0),
                    arm.armToPosition(0,0,5),
                    new SleepAction(1),
                    moveToBasket,
                    arm.armToPosition(70,0,1000),
                    arm.armToPosition(70,20,5),
                    arm.setIntake(arm.INTAKE_OUT),
                    new SleepAction(1),
                    arm.setIntake(0)

                )
        );
    }
}