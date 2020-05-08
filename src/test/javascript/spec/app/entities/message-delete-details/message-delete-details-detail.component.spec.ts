import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MessageDeleteDetailsDetailComponent } from 'app/entities/message-delete-details/message-delete-details-detail.component';
import { MessageDeleteDetails } from 'app/shared/model/message-delete-details.model';

describe('Component Tests', () => {
  describe('MessageDeleteDetails Management Detail Component', () => {
    let comp: MessageDeleteDetailsDetailComponent;
    let fixture: ComponentFixture<MessageDeleteDetailsDetailComponent>;
    const route = ({ data: of({ messageDeleteDetails: new MessageDeleteDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MessageDeleteDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MessageDeleteDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MessageDeleteDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load messageDeleteDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.messageDeleteDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
