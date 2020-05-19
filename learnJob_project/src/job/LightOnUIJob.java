package job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;

import learnJob.JobManagerUtil;

public class LightOnUIJob extends UIJob {

	public LightOnUIJob(String name) {
		super(name);
	}

	public LightOnUIJob() {
		super("Light on ui job");
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		JobManagerUtil.print("start ==> Turning on the light in the ui job.");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JobManagerUtil.print("end ==> Turning on the light in the ui job.");
		return Status.OK_STATUS;
	}

}
