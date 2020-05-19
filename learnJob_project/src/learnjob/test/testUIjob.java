package learnjob.test;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import job.LightOffJobWithNonRull;
import job.LightOnJobWithNonRull;
import job.LightOnUIJob;
import learnJob.JobManagerUtil;

public class testUIjob {
	public static void main(String[] args) {
//		((JobManager)(Job.getJobManager())).optionsChanged(new DebugOptionImp());
		testSingleUIjob();
//		testDifferentInstance1();
	}

	private static void testSingleUIjob() {
		LightOnUIJob lightOn1 = new LightOnUIJob();
		
		lightOn1.addJobChangeListener(new IJobChangeListener() {
			
			@Override
			public void sleeping(IJobChangeEvent event) {
				JobManagerUtil.print("call the sleeping method after receive the event");
				JobManagerUtil.printAllJobs();
			}

			/**
			 *  [main] : SLEEPING == >
				[main] : WAITING == >
				[main] : RUNNING == >
			 */
			@Override
			public void scheduled(IJobChangeEvent event) {
				JobManagerUtil.print("call the scheduled method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the running method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void running(IJobChangeEvent event) {
				JobManagerUtil.print("call the running method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				JobManagerUtil.print("call the done method after receive the event");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Job lightoffJob = new LightOffJobWithNonRull();
				lightoffJob.schedule();
				
				JobManagerUtil.printAllJobs();
			}
			
			@Override
			public void awake(IJobChangeEvent event) {
				JobManagerUtil.print("call the awake method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the aboutToRun method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				JobManagerUtil.print("call the aboutToRun method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
		});
		
		lightOn1.schedule();

		JobManagerUtil.waitForJobs(3000);
		
		try {
				Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JobManagerUtil.printAllJobs();
	}
	
	private static void testDifferentInstance1() throws InterruptedException {
		LightOnJobWithNonRull lightOn1 = new LightOnJobWithNonRull();
		
		lightOn1.addJobChangeListener(new IJobChangeListener() {
			
			@Override
			public void sleeping(IJobChangeEvent event) {
				JobManagerUtil.print("call the sleeping method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			/**
			 *  [main] : SLEEPING == >
				[main] : WAITING == >
				[main] : RUNNING == >
			 */
			@Override
			public void scheduled(IJobChangeEvent event) {
				JobManagerUtil.print("call the scheduled method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the running method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void running(IJobChangeEvent event) {
				JobManagerUtil.print("call the running method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				JobManagerUtil.printAllJobs();

				JobManagerUtil.print("call the done method after receive the event");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				JobManagerUtil.printAllJobs();
			}
			
			@Override
			public void awake(IJobChangeEvent event) {
				JobManagerUtil.print("call the awake method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the aboutToRun method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				JobManagerUtil.print("call the aboutToRun method after receive the event");
				JobManagerUtil.printAllJobs();
			}
			
		});
		
		lightOn1.schedule();
		
		Thread.sleep(1000);  // result
		lightOn1.schedule();
		
		JobManagerUtil.waitForJobs(2000);
		
		Thread.sleep(5000);
		JobManagerUtil.printAllJobs();
	}
}
