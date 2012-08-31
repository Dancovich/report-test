package br.gov.serpro.report.report_test_war.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.FileRenderer;

import br.gov.serpro.report.report_test_war.business.BookmarkBC;
import br.gov.serpro.report.report_test_war.domain.Bookmark;

@ViewController
@NextView("/bookmark_edit.xhtml")
@PreviousView("/bookmark_list.xhtml")
public class BookmarkListMB extends AbstractListPageBean<Bookmark, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private BookmarkBC bc;
	
	@Inject
	private FileRenderer renderer;

	@Override
	protected List<Bookmark> handleResultList() {
		return this.bc.findAll();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);

			if (delete) {
				bc.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	public void viewBookmarkReport(){
		byte[] report = bc.loadReport();
		renderer.render(report, FileRenderer.ContentType.PDF, "report.pdf", true);
		//return getNextView();
	}

}
