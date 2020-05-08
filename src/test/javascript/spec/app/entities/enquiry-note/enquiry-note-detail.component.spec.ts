import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryNoteDetailComponent } from 'app/entities/enquiry-note/enquiry-note-detail.component';
import { EnquiryNote } from 'app/shared/model/enquiry-note.model';

describe('Component Tests', () => {
  describe('EnquiryNote Management Detail Component', () => {
    let comp: EnquiryNoteDetailComponent;
    let fixture: ComponentFixture<EnquiryNoteDetailComponent>;
    const route = ({ data: of({ enquiryNote: new EnquiryNote(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryNoteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnquiryNoteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnquiryNoteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enquiryNote on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enquiryNote).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
