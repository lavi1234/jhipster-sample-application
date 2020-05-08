import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { EnquiryDetailsDetailComponent } from 'app/entities/enquiry-details/enquiry-details-detail.component';
import { EnquiryDetails } from 'app/shared/model/enquiry-details.model';

describe('Component Tests', () => {
  describe('EnquiryDetails Management Detail Component', () => {
    let comp: EnquiryDetailsDetailComponent;
    let fixture: ComponentFixture<EnquiryDetailsDetailComponent>;
    const route = ({ data: of({ enquiryDetails: new EnquiryDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [EnquiryDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EnquiryDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EnquiryDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load enquiryDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.enquiryDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
