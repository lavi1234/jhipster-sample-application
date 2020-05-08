import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { IEnquiry, Enquiry } from 'app/shared/model/enquiry.model';

describe('Service Tests', () => {
  describe('Enquiry Service', () => {
    let injector: TestBed;
    let service: EnquiryService;
    let httpMock: HttpTestingController;
    let elemDefault: IEnquiry;
    let expectedResult: IEnquiry | IEnquiry[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EnquiryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Enquiry(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            offerTaxtUntil: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Enquiry', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            offerTaxtUntil: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            offerTaxtUntil: currentDate
          },
          returnedFromService
        );

        service.create(new Enquiry()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Enquiry', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            deliveryTerms: 'BBBBBB',
            offerTaxtUntil: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            offerTaxtUntil: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Enquiry', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            deliveryTerms: 'BBBBBB',
            offerTaxtUntil: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            offerTaxtUntil: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Enquiry', () => {
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
