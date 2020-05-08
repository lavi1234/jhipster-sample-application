import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { EnquiryNoteComponent } from './enquiry-note.component';
import { EnquiryNoteDetailComponent } from './enquiry-note-detail.component';
import { EnquiryNoteUpdateComponent } from './enquiry-note-update.component';
import { EnquiryNoteDeleteDialogComponent } from './enquiry-note-delete-dialog.component';
import { enquiryNoteRoute } from './enquiry-note.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(enquiryNoteRoute)],
  declarations: [EnquiryNoteComponent, EnquiryNoteDetailComponent, EnquiryNoteUpdateComponent, EnquiryNoteDeleteDialogComponent],
  entryComponents: [EnquiryNoteDeleteDialogComponent]
})
export class JhipsterSampleApplicationEnquiryNoteModule {}
