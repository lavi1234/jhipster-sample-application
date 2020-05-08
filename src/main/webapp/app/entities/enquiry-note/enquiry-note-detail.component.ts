import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnquiryNote } from 'app/shared/model/enquiry-note.model';

@Component({
  selector: 'jhi-enquiry-note-detail',
  templateUrl: './enquiry-note-detail.component.html'
})
export class EnquiryNoteDetailComponent implements OnInit {
  enquiryNote: IEnquiryNote | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryNote }) => (this.enquiryNote = enquiryNote));
  }

  previousState(): void {
    window.history.back();
  }
}
