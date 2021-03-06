package job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import learnJob.JobManagerUtil;

public class LightOnJobWithNonRull extends Job {
	public static final Object JOB_FAMILY = new Object();
	
	public LightOnJobWithNonRull() {
		super("Light on job without rule.");
	}

	@Override
	public IStatus run(IProgressMonitor monitor) {
		JobManagerUtil.print("start ==> Turning on the light without rule.");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		JobManagerUtil.print("end ==> Turning on the light without rule.");
		return Status.OK_STATUS;
	}
	
	@Override
	public boolean belongsTo(Object family) {
		return JOB_FAMILY.equals(family);
	}
}
