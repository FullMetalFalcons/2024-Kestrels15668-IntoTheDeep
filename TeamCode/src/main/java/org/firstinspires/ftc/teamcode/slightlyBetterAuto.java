/*
package org.firstinspires.ftc.teamcode;

// Road Runner Specific Imports
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous
public class slightlyBetterAuto extends LinearOpMode {


    public void runOpMode() {
        // Initialize subsystems
        KestrelArm kestrelArm = new KestrelArm(hardwareMap, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(34.6, 63.3, Math.toRadians(-90)));
        KestrelArm.Claw.setPosition(0.4);
        KestrelArm.ClawRotator.setPosition(0.03);
        sleep(400);

        //wait 3 seconds
        Action waitAction = new KestrelArm.WaitAction(3);

        // Build the first trajectory segment
        Action trajectory1 = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(70, 56), Math.toRadians(-90))  // Move towards basket
                .waitSeconds(0.6)
                .turn(Math.toRadians(115)) // Turn to basket
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(65, 52), Math.toRadians(-90)) // Move backwards
                .turn(Math.toRadians(160))
                .build();

        // Build the arm movement action (moves arm to position 600 over 2 seconds)
        Action armExtend = new KestrelArm.ArmRotatorToPosition(kestrelArm, 200, 2.0);

        Action clawOpen = new KestrelArm.ClawAction(kestrelArm, 0.0);

        Action clawClose = new KestrelArm.ClawAction(kestrelArm, 0.4);
        Action armUnExtend = new KestrelArm.ArmRotatorToPosition(kestrelArm, 0, 2.0);

        Action liftUp = new KestrelArm.LiftToPosition(kestrelArm, 200, 2.0); // Move lift up
        Action liftDown = new KestrelArm.LiftToPosition(kestrelArm, 0, 2.0); // Lower lift

        Action slideExtend = new KestrelArm.SlideToPosition(kestrelArm, 300, 2.0); // Extend slide
        Action slideRetract = new KestrelArm.SlideToPosition(kestrelArm, 0, 2.0); // Retract slide



        // Build the second trajectory segment
        Action park = drive.actionBuilder(drive.pose)
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-58.5, 60), Math.toRadians(-90))  //Park
                .build();

        // Combine all actions into a single sequential action
        Action auto = new SequentialAction(
                clawClose,
                waitAction,    // Wait for 3 seconds before starting
                trajectory1,
                armExtend,
                slideExtend,
                liftUp,
                clawOpen,
                armUnExtend,
                park
        );

        waitForStart();
        if (isStopRequested()) return;

        // Run the complete autonomous sequence
        Actions.runBlocking(auto);
    }

    public void extendArm(KestrelArm kestrelArm) {
        Action armMove = new KestrelArm.ArmRotatorToPosition(kestrelArm, 600, 2.0);
        Actions.runBlocking(armMove); // Execute the arm move action
    }

    public void closeArm(KestrelArm kestrelArm) {
        Action armMove = new KestrelArm.ArmRotatorToPosition(kestrelArm, 0, 2.0); // Moves arm to position 0
        Actions.runBlocking(armMove); // Execute the arm move action
    }
}
*/