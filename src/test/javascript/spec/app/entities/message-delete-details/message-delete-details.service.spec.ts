import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MessageDeleteDetailsService } from 'app/entities/message-delete-details/message-delete-details.service';
import { IMessageDeleteDetails, MessageDeleteDetails } from 'app/shared/model/message-delete-details.model';

describe('Service Tests', () => {
  describe('MessageDeleteDetails Service', () => {
    let injector: TestBed;
    let service: MessageDeleteDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IMessageDeleteDetails;
    let expectedResult: IMessageDeleteDetails | IMessageDeleteDetails[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MessageDeleteDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MessageDeleteDetails(0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            deletedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MessageDeleteDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            deletedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate
          },
          returnedFromService
        );

        service.create(new MessageDeleteDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MessageDeleteDetails', () => {
        const returnedFromService = Object.assign(
          {
            deletedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MessageDeleteDetails', () => {
        const returnedFromService = Object.assign(
          {
            deletedAt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MessageDeleteDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
