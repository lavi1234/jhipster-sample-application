import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryDetailComponent } from 'app/entities/enquiry/enquiry-detail.component';
import { Enquiry } from 'app/shared/model/enquiry.model';

describe('Component Tests', () => {
  describe('Enquiry Management Detail Component', () => {
    let comp: EnquiryDetailComponent;
    let fixture: ComponentFixture<EnquiryDetailComponent>;
    const route = ({ data: of({ enquiry: new Enquiry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnquiryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnquiryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enquiry on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enquiry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
